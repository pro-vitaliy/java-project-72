@import hexlet.code.dto.urls.UrlPage
@import hexlet.code.util.NamedRoutes
@param UrlPage page

@template.layout.layout(basePage = page, content = @`
  <div class="container-lg mt-5">
    @if(page != null)
      <h1>Сайт ${page.getUrl().getName()}</h1>
      <table class="table">
        <tbody>
          <tr>
            <th>ID</th>
            <th>${page.getUrl().getId()}</th>
          </tr>
          <tr>
            <th>Имя</th>
            <th>${page.getUrl().getName()}</th>
          </tr>
          <tr>
            <th>Дата создания</th>
            <th>${page.getUrl().getFormattedCreatedAt()}</th>
          </tr>
        </tbody>
      </table>
      <form action="${NamedRoutes.urlCheckPath(page.getUrl().getId())}" method="post">
        <label>
          <input type="submit" value="Check" class="btn btn-primary h-100 px-sm-5">
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
              <td>${check.getFormattedCreatedAt()}</td>
            </tr>
          @endfor
          </tbody>
        @endif
      </table>
    @endif
  </div>
`)
