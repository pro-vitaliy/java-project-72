@import hexlet.code.dto.urls.UrlPage
@param UrlPage page

@template.layout.layout(content = @`
  <ul>
    @if(page != null)
      <li>
        ID: ${page.getUrl().getId()}
      </li>
      <li>
        NAME: ${page.getUrl().getName()}
      </li>
      <li>
        CREATED: ${page.getUrl().getCreatedAt().toString()}
      </li>
    @endif
  </ul>
`)
