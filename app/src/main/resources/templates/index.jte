@import hexlet.code.dto.BasePage
@import hexlet.code.util.NamedRoutes
@param BasePage page

@template.layout.layout(basePage = page, content = @`
  <div class="container p-5 mt-5">
    <div class="row">
      <div class="col-md-10 col-lg-8 mx-auto">
        <h1 class="display-4">Анализатор страниц</h1>
        <p class="lead">Бесплатно проверяйте сайты на SEO пригодность</p>
        <form action="${NamedRoutes.urlsPath()}" method="post" class="rss-form text-body">
          <div class="row">
            <div class="col">
              <div class="form-floating">
                <input type="text"
                       autofocus
                       required
                       name="url"
                       aria-label="url"
                       class="form-control w-100"
                       id="inputUrl"
                       autocomplete="off"
                       placeholder="Ссылка">
                <label for="inputUrl">Ссылка</label>
              </div>
            </div>
            <div class="col-auto">
              <button type="submit" class="btn btn-primary btn-lg h-100 px-sm-5">Проверить</button>
            </div>
          </div>
        </form>
      </div>
    </div>
  </div>
`)