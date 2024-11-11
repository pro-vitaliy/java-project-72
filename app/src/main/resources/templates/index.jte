@import hexlet.code.dto.BasePage
@import hexlet.code.util.NamedRoutes
@param BasePage page

@template.layout.layout(basePage = page, content = @`
  <form action="${NamedRoutes.urlsPath()}" method="post">
    <div class="mb-3">
      <label class="form-label">
        Enter url
        <input type="text" name="url" class="form-control">
      </label>
      <span>
        <input type="submit" class="btn btn-primary" value="Submit">
      </span>
    </div>
  </form>
`)