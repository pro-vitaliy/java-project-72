package hexlet.code.repository;

import hexlet.code.model.Url;

import java.sql.SQLException;

public class UrlRepository extends BaseRepository {
    public static void save(Url url) throws SQLException {
        String sql = "INSERT INTO urls (name, created_at) VALUES (?, CURRENT_TIMESTAMP) RETURNING id, created_at;";
        try (var connection = dataSource.getConnection();
                var statement = connection.prepareStatement(sql)) {
            statement.setString(1, url.getName());
            try (var resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    url.setId(resultSet.getLong("id"));
                    url.setCreatedAt(resultSet.getTimestamp("created_at"));
                }
            }
        }
    }
}
