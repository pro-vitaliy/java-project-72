package hexlet.code.controller;

import hexlet.code.dto.urls.UrlsPage;
import hexlet.code.model.Url;
import hexlet.code.repository.UrlRepository;
import io.javalin.http.Context;

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
            var url = new Url(parsedUrl);
            UrlRepository.save(url);
            ctx.redirect("/urls");
        } catch (URISyntaxException | MalformedURLException e) {
            ctx.render("index.jte");
        }
    }

    public static void index(Context ctx) throws SQLException {
        var page = new UrlsPage(UrlRepository.getEntities());
        ctx.render("urls/index.jte", model("page", page));
    }

    public static void show(Context ctx) {
        var id = ctx.pathParamAsClass("id", Long.class).get();
//        UrlRepository.find(id);
    }
}
