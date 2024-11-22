package hexlet.code.dto.urls;

import hexlet.code.dto.BasePage;
import hexlet.code.model.Url;

import java.util.Map;

import hexlet.code.model.UrlCheck;
import lombok.Getter;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Getter
public class UrlsPage extends BasePage {
    private Map<Url, UrlCheck> urls;
}
