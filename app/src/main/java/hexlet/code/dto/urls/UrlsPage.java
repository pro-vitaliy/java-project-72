package hexlet.code.dto.urls;

import hexlet.code.model.Url;

import java.util.List;
import lombok.Getter;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Getter
public class UrlsPage {
    private List<Url> urls;
}
