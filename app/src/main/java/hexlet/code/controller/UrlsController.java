package hexlet.code.controller;

import hexlet.code.dto.BasePage;
import hexlet.code.dto.urls.UrlPage;
import hexlet.code.dto.urls.UrlsPage;
import hexlet.code.model.Url;
import hexlet.code.repository.UrlRepository;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

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
                ctx.redirect("/urls");
            } else {
                var url = new Url(parsedUrl);
                UrlRepository.save(url);
                ctx.sessionAttribute("flash", "Страница успешно добавлена");
                ctx.redirect("/urls");
            }
        } catch (URISyntaxException | MalformedURLException | IllegalArgumentException e) {
            ctx.sessionAttribute("flash", "Некорректный URL");
            var page = new BasePage();
            page.setFlash(ctx.consumeSessionAttribute("flash"));
            ctx.render("index.jte", model("page", page));
        }
    }

    public static void index(Context ctx) throws SQLException {
        var page = new UrlsPage(UrlRepository.getEntities());
        page.setFlash(ctx.consumeSessionAttribute("flash"));
        ctx.render("urls/index.jte", model("page", page));
    }

    public static void show(Context ctx) throws SQLException {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var url = UrlRepository.find(id)
                .orElseThrow(() -> new NotFoundResponse("Url with id=" + id + "not found"));
        var page = new UrlPage(url);
        ctx.render("urls/show.jte", model("page", page));
    }
}
