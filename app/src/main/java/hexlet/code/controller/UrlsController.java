package hexlet.code.controller;

import hexlet.code.dto.BasePage;
import hexlet.code.dto.urls.UrlPage;
import hexlet.code.dto.urls.UrlsPage;
import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import hexlet.code.util.NamedRoutes;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.LinkedHashMap;

import org.jsoup.Jsoup;

import static io.javalin.rendering.template.TemplateUtil.model;

public class UrlsController {
    public static void create(Context ctx) throws SQLException {
        var formParam = ctx.formParamAsClass("url", String.class).get().trim().toLowerCase();
        try {
            var uri = new URI(formParam);
            var inputUrl = uri.toURL();

            var protocol = inputUrl.getProtocol();
            var host = inputUrl.getHost();
            var port = inputUrl.getPort();

            var parsedUrl = String.format("%s://%s", protocol, host);
            if (port != -1) {
                parsedUrl = parsedUrl + ":" + port;
            }
            if (UrlRepository.urlContains(parsedUrl)) {
                ctx.sessionAttribute("flash", "Страница уже существует");
                ctx.redirect(NamedRoutes.urlsPath());
            } else {
                var url = new Url(parsedUrl);
                UrlRepository.save(url);
                ctx.sessionAttribute("flash", "Страница успешно добавлена");
                ctx.redirect(NamedRoutes.urlsPath());
            }
        } catch (URISyntaxException | MalformedURLException | IllegalArgumentException e) {
            ctx.sessionAttribute("flash", "Некорректный URL");
            var page = new BasePage();
            page.setFlash(ctx.consumeSessionAttribute("flash"));
            ctx.render("index.jte", model("page", page)).status(302);
        }
    }

    public static void index(Context ctx) throws SQLException {
        var urls = UrlRepository.getEntities();
        var urlsInfo = new LinkedHashMap<Url, UrlCheck>();
        for (var url : urls) {
            var lastCheck = UrlCheckRepository.findLastCheck(url.getId())
                    .orElse(new UrlCheck(url.getId()));
            urlsInfo.put(url, lastCheck);
        }
        var page = new UrlsPage(urlsInfo);
        page.setFlash(ctx.consumeSessionAttribute("flash"));
        ctx.render("urls/index.jte", model("page", page));
    }

    public static void show(Context ctx) throws SQLException {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var url = UrlRepository.find(id)
                .orElseThrow(() -> new NotFoundResponse("Url with id=" + id + "not found"));
        var urlCheck = UrlCheckRepository.findByUrlId(id);
        var page = new UrlPage(url, urlCheck);
        page.setFlash(ctx.consumeSessionAttribute("flash"));
        ctx.render("urls/show.jte", model("page", page));
    }

    public static void check(Context ctx) throws SQLException, IOException {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var url = UrlRepository.find(id)
                .orElseThrow(() -> new NotFoundResponse("URL not found"));

        var urlCheck = new UrlCheck(id);
        try {
            var response = Jsoup.connect(url.getName()).execute();
            var statusCode = response.statusCode();
            urlCheck.setStatusCode(statusCode);
            var doc = response.parse();
            urlCheck.setTitle(doc.title());

            var h1Elem = doc.selectFirst("h1");
            var h1 = h1Elem != null ? h1Elem.text() : "";
            urlCheck.setH1(h1);

            var descElem = doc.selectFirst("meta[name=description]");
            var description = descElem != null ? descElem.attr("content") : "";
            urlCheck.setDescription(description);
            UrlCheckRepository.save(urlCheck);
            ctx.sessionAttribute("flash", "Проверка успешно добавлена");
        } catch (UnknownHostException e) {
            ctx.sessionAttribute("flash", "Некорректный адрес");
        }

        ctx.redirect(NamedRoutes.urlPath(id));
    }
}
