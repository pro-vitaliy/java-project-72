package hexlet.code.repository;

import hexlet.code.model.Url;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UrlRepository extends BaseRepository {
    public static void save(Url url) throws SQLException {
        var sql = "INSERT INTO urls (name, created_at) VALUES (?, ?)";
        try (var connection = dataSource.getConnection();
                var statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, url.getName());
            var timeStamp = Timestamp.valueOf(LocalDateTime.now());
            statement.setTimestamp(2, timeStamp);
            statement.executeUpdate();
            var generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                url.setId(generatedKeys.getLong("id"));
            } else {
                throw new SQLException("DB have not returned an id after saving an entity");
            }
        }
    }

    public static List<Url> getEntities() throws SQLException {
        var sql = "SELECT * FROM urls";
        try (var connection = dataSource.getConnection();
                var statement = connection.prepareStatement(sql)) {
            var resultSet = statement.executeQuery();
            var result = new ArrayList<Url>();
            while (resultSet.next()) {
                var id = resultSet.getLong("id");
                var name = resultSet.getString("name");
                var createdAt = resultSet.getTimestamp("created_at");
                var url = new Url(name);
                url.setId(id);
                url.setCreatedAt(createdAt);
                result.add(url);
            }
            return result;
        }
    }
    public static Optional<Url> find(Long id) throws SQLException {
        var sql = "SELECT * FROM urls WHERE id = ?";
        try (var connection = dataSource.getConnection();
                var statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                var name = resultSet.getString("name");
                var createdAt = resultSet.getTimestamp("created_at");
                var url = new Url(name);
                url.setId(id);
                url.setCreatedAt(createdAt);
                return Optional.of(url);
            }
            return Optional.empty();
        }
    }
}
