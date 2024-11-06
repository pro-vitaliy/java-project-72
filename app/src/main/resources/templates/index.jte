@template.layout.layout(content = @`
  <form action="/urls" method="post">
    <div class="mb-3">
      <label class="form-label">
        Enter url
        <input type="url" name="url" class="form-control">
      </label>
      <span>
        <input type="submit" class="btn btn-primary" value="Submit">
      </span>
    </div>
  </form>
`)