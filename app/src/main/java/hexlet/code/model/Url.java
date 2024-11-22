package hexlet.code.model;

import java.sql.Timestamp;

import hexlet.code.util.TimestampFormatter;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Url {
    private Long id;
    private String name;
    private Timestamp createdAt;

    public Url(String name) {
        this.name = name;
    }

    public String getFormattedCreatedAt() {
        return TimestampFormatter.format(createdAt);
    }
}
