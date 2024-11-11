@import hexlet.code.dto.urls.UrlsPage
@import hexlet.code.util.NamedRoutes
@param UrlsPage page

@template.layout.layout(basePage = page, content = @`
  <p>Здесь будет таблица</p>
  @if(!page.getUrls().isEmpty())
    @for(var url : page.getUrls())
      <a href="${NamedRoutes.urlPath(url.getId())}">${url.getName()}</a>
    @endfor
  @endif
`)
