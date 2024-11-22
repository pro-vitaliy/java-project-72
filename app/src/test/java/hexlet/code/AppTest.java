package hexlet.code;

import hexlet.code.model.Url;
import hexlet.code.repository.UrlRepository;
import hexlet.code.util.NamedRoutes;
import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;


public class AppTest {
    private static Javalin app;
    private static MockWebServer mockServer;

    @BeforeAll
    public static void beforeAll() throws IOException {
        mockServer = new MockWebServer();
        MockResponse mockedResponse = new MockResponse()
                .setBody(readFixture("test.html"));
        mockServer.enqueue(mockedResponse);
        mockServer.start();
    }

    private static String readFixture(String fixturePath) throws IOException {
        return Files.readString(Paths.get("src/test/resources/" + fixturePath));
    }

    @BeforeEach
    public final void setApp() throws SQLException, FileNotFoundException {
        app = App.getApp();
    }

    @Test
    public void testMainPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get(NamedRoutes.rootPath());
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body()).isNotNull();
            var responseBody = response.body().string();
            assertThat(responseBody).contains("<form");
            assertThat(responseBody).contains("name=\"url\"");
        });
    }

    @Test
    public void testAddUrl() {
        JavalinTest.test(app, (server, client) -> {
            var requestBody = "url=https://test.io";
            try (var response = client.post(NamedRoutes.urlsPath(), requestBody)) {
                assertThat(response.code()).isEqualTo(200);
                assertThat(response.body()).isNotNull();
                assertThat(response.body().string()).contains("https://test.io");
            }
        });
    }

    @Test
    public void testUrlsPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get(NamedRoutes.urlsPath());
            assertThat(response.code()).isEqualTo(200);
        });
    }

    @Test
    public void testUrlPage() throws SQLException {
        var url = new Url("https://test.io");
        UrlRepository.save(url);
        JavalinTest.test(app, (server, client) -> {
            var response = client.get(NamedRoutes.urlPath(url.getId()));
            assertThat(response.code()).isEqualTo(200);
        });
    }

    @Test
    public void testUrlNotFound() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get(NamedRoutes.urlPath(0L));
            assertThat(response.code()).isEqualTo(404);
        });
    }

    @Test
    public void testUrlCheck() throws SQLException {
        var url = new Url(mockServer.url("/").toString());
        UrlRepository.save(url);
        JavalinTest.test(app, (server, client) -> {
            try (var response = client.post(NamedRoutes.urlCheckPath(url.getId()))) {
                assertThat(response.code()).isEqualTo(200);
                assertThat(response.body()).isNotNull();
                var responseBody = response.body().string();
                assertThat(responseBody).contains("Test description");
                assertThat(responseBody).contains("Test title");
                assertThat(responseBody).contains("Test h1");
            }
        });
    }

    @AfterAll
    public static void shutDown() throws IOException {
        mockServer.shutdown();
    }
}
