package hexlet.code.repository;

import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class UrlCheckRepository extends BaseRepository {
    public static void save(UrlCheck check) throws SQLException {
        var sql = """
                INSERT INTO url_checks
                (url_id, status_code, h1, title, description, created_at)
                VALUES (?, ?, ?, ?, ?, ?);
                """;

        try (var connection = dataSource.getConnection();
                var statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, check.getUrlId());
            statement.setInt(2, check.getStatusCode());
            statement.setString(3, check.getH1());
            statement.setString(4, check.getTitle());
            statement.setString(5, check.getDescription());
            var timeStamp = Timestamp.valueOf(LocalDateTime.now());
            statement.setTimestamp(6, timeStamp);
            statement.executeUpdate();
            var generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                check.setId(generatedKeys.getLong(1));
            } else {
                throw new SQLException("DB have not returned an id after saving an entity");
            }
        }
    }

    public static List<UrlCheck> findByUrlId(Long urlId) throws SQLException {
        var sql = "SELECT * FROM url_checks WHERE url_id = ?";

        try (var connection = dataSource.getConnection();
                var statement = connection.prepareStatement(sql)) {
            statement.setLong(1, urlId);
            var result = new ArrayList<UrlCheck>();
            var resultSet = statement.executeQuery();
            while (resultSet.next()) {
                var urlCheck = new UrlCheck(urlId);
                urlCheck.setId(resultSet.getLong("id"));
                urlCheck.setStatusCode(resultSet.getInt("status_code"));
                urlCheck.setTitle(resultSet.getString("title"));
                urlCheck.setH1(resultSet.getString("h1"));
                urlCheck.setDescription(resultSet.getString("description"));
                urlCheck.setCreatedAt(resultSet.getTimestamp("created_at"));
                result.add(urlCheck);
            }
            return result;
        }
    }

    public static Map<Url, UrlCheck> getUrlsLastInfo() throws SQLException {
        var sql = """
                SELECT DISTINCT ON (url_id)
                    urls.id AS url_id,
                    url_checks.id AS check_id,
                    url_checks.status_code,
                    url_checks.title,
                    url_checks.h1,
                    url_checks.description,
                    url_checks.created_at
                FROM urls
                LEFT JOIN url_checks ON
                    urls.id = url_checks.url_id
                ORDER BY url_id, url_checks.created_at DESC;""";
        var result = new LinkedHashMap<Url, UrlCheck>();
        try (var connection = dataSource.getConnection();
                var statement = connection.prepareStatement(sql)) {
            var resultSet = statement.executeQuery();
            while (resultSet.next()) {
                var urlId = resultSet.getLong("url_id");
                var url = UrlRepository.find(urlId)
                        .orElseThrow(() -> new SQLException("Url id exists in url_checks, but not in urls table"));
                var lastCheck = new UrlCheck(urlId);
                lastCheck.setId(resultSet.getLong("check_id"));
                lastCheck.setStatusCode(resultSet.getInt("status_code"));
                lastCheck.setTitle(resultSet.getString("title"));
                lastCheck.setH1(resultSet.getString("h1"));
                lastCheck.setDescription(resultSet.getString("description"));
                lastCheck.setCreatedAt(resultSet.getTimestamp("created_at"));

                result.put(url, lastCheck);
            }
        }
        return result;
    }
}
