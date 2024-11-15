@import hexlet.code.dto.urls.UrlPage
@import hexlet.code.util.NamedRoutes
@param UrlPage page

@template.layout.layout(basePage = page, content = @`
  @if(page != null)
    <ul>
      <li>
        ID: ${page.getUrl().getId()}
      </li>
      <li>
        NAME: ${page.getUrl().getName()}
      </li>
      <li>
        CREATED: ${page.getUrl().getCreatedAt().toString()}
      </li>
    </ul>
    <form action="${NamedRoutes.urlCheckPath(page.getUrl().getId())}" method="post">
      <label>
        <input type="submit" value="Check" class="btn btn-primary">
      </label>
    </form>
    <table class="table">
      <thead>
      <tr>
        <th scope="col">ID</th>
        <th scope="col">Код ответа</th>
        <th scope="col">Title</th>
        <th scope="col">h1</th>
        <th scope="col">Description</th>
        <th scope="col">Дата проверки</th>
      </tr>
      </thead>
      @if(!page.getChecks().isEmpty())
        <tbody>
        @for(var check : page.getChecks())
          <tr>
            <th scope="row">${check.getId()}</th>
            <td>${check.getStatusCode()}</td>
            <td>${check.getTitle()}</td>
            <td>${check.getH1()}</td>
            <td>${check.getDescription()}</td>
            <td>${check.getCreatedAt().toString()}</td>
          </tr>
        @endfor
        </tbody>
      @endif
    </table>
  @endif
`)
