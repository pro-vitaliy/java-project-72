package hexlet.code;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.ResourceCodeResolver;
import hexlet.code.repository.BaseRepository;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) throws Exception {
        int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "7070"));
        getApp().start(port);
    }

    public static void initializeDataSource() throws Exception {
        var hikariConfig = new HikariConfig();
        var defaultJdbcUrl = "jdbc:h2:mem:project;DB_CLOSE_DELAY=-1;";
        var dbUrl = System.getenv().getOrDefault("JDBC_DATABASE_URL", defaultJdbcUrl);
//        var dbUrl = "jdbc:h2:mem:project;DB_CLOSE_DELAY=-1;";
//        var dbUrlTemplate = System.getenv("JDBC_DATABASE_URL");
//        if (dbUrlTemplate != null) {
//            dbUrl = dbUrlTemplate
//                    .replace("HOST", System.getenv("HOST"))
//                    .replace("DB_PORT", System.getenv("DB_PORT"))
//                    .replace("DATABASE", System.getenv("DATABASE"))
//                    .replace("PASSWORD", System.getenv("PASSWORD"))
//                    .replace("USERNAME", System.getenv("USERNAME"));
//        }

        hikariConfig.setJdbcUrl(dbUrl);

        var dataSource = new HikariDataSource(hikariConfig);
        var url = App.class.getClassLoader().getResourceAsStream("schema.sql");
        var sql = new BufferedReader(new InputStreamReader(url))
                .lines().collect(Collectors.joining("\n"));
        try (var conn = dataSource.getConnection();
                var statement = conn.createStatement()) {
            statement.execute(sql);
        }
        BaseRepository.dataSource = dataSource;
    }

    public static Javalin getApp() throws Exception {
        initializeDataSource();

        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
            config.fileRenderer(new JavalinJte(createTemplateEngine()));
        });

        app.get("/", ctx -> ctx.render("index.jte"));

        app.post("/urls", ctx -> {
            var formParam = ctx.formParamAsClass("url", URL.class).get();
            var url = formParam.toURI();
        });

        return app;
    }

    private static TemplateEngine createTemplateEngine() {
        ClassLoader classLoader = App.class.getClassLoader();
        ResourceCodeResolver codeResolver = new ResourceCodeResolver("templates", classLoader);
        return TemplateEngine.create(codeResolver, ContentType.Html);
    }

}
