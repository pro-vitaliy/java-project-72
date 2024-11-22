package hexlet.code.util;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;

public class TimestampFormatter {
    public static String format(Timestamp timestamp) {
        if (timestamp == null) {
            return "";
        }
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return timestamp.toLocalDateTime().format(formatter);
    }
}
