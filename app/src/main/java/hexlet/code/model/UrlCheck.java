package hexlet.code.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class UrlCheck {
    private Long id;
    private int statusCode;
    private String title;
    private String h1;
    private String description;
    private Long urlId;
    private Timestamp createdAt;

    public UrlCheck(Long urlId) {
        this.urlId = urlId;
    }
}
