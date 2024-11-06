@import hexlet.code.dto.urls.UrlsPage
@param UrlsPage page

@template.layout.layout(content = @`
  <p>Здесь будет таблица</p>
  @if(!page.getUrls().isEmpty())
    @for(var url : page.getUrls())
      <a href="/urls/${url.getId()}">${url.getName()}</a>
    @endfor
  @endif
  
`)
