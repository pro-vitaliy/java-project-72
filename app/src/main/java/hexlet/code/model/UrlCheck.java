package hexlet.code.model;

import hexlet.code.util.TimestampFormatter;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class UrlCheck {
    private Long id;
    private Integer statusCode;
    private String title;
    private String h1;
    private String description;
    private Long urlId;
    private Timestamp createdAt;

    public UrlCheck(Long urlId) {
        this.urlId = urlId;
    }

    /**
     * Formats the creation at timestamp
     * <p>
     *     Subclasses may override this method to provide a custom formatting logic.
     * </p>
     * @return formatted creation timestamp as a String
     */
    public String getFormattedCreatedAt() {
        return TimestampFormatter.format(createdAt);
    }
}
