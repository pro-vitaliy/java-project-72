package hexlet.code.repository;

import hexlet.code.model.UrlCheck;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UrlCheckRepository extends BaseRepository {
    public static void save(UrlCheck check) throws SQLException {
        var sql = """
                INSERT INTO urls_check
                (url_id, status_code, h1, title, description, created_at)
                VALUES (?, ?, ?, ?, ?, ?);
                """;

        try (var connection = dataSource.getConnection();
                var statement = connection.prepareStatement(sql)) {
            statement.setLong(1, check.getUrlId());
            statement.setInt(2, check.getStatusCode());
            statement.setString(3, check.getH1());
            statement.setString(4, check.getTitle());
            statement.setString(5, check.getDescription());
            var timeStamp = Timestamp.valueOf(LocalDateTime.now());
            statement.setTimestamp(6, timeStamp);
            statement.executeUpdate();
        }
    }

    public static List<UrlCheck> find(Long urlId) throws SQLException {
        var sql = "SELECT * FROM urls_check WHERE url_id = ?";

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
}
