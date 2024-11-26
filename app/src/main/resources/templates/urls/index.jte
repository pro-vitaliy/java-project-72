@import hexlet.code.dto.urls.UrlsPage
@import hexlet.code.util.NamedRoutes
@param UrlsPage page

@template.layout.layout(basePage = page, content = @`
  <div class="container-lg mt-5">
    <div>
      <div>
        <h1>Добавленные сайты</h1>
        <table class="table table-hover mt-3">
          <thead>
            <tr>
              <th scope="col">ID</th>
              <th scope="col">Имя</th>
              <th scope="col">Последняя проверка</th>
              <th scope="col">Код ответа</th>
            </tr>
          </thead>
          <tbody>
            @if(!page.getUrls().isEmpty())
              @for(var entry : page.getUrls().entrySet())
                <tr>
                  <td>${entry.getKey().getId()}</td>
                  <td>
                    <a href="${NamedRoutes.urlPath(entry.getKey().getId())}">${entry.getKey().getName()}</a>
                  </td>
                  <td>
                    ${entry.getValue().getFormattedCreatedAt()}
                  </td>
                  <td>
                    @if(entry.getValue().getStatusCode() != 0)
                      ${entry.getValue().getStatusCode()}
                    @endif
                  </td>
                </tr>
              @endfor
            @endif
          </tbody>
        </table>
      </div>
    </div>
  </div>
`)
