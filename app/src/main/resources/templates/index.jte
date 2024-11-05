<!doctype html>
<html lang="ru">
  <head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>My project</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
          crossorigin="anonymous">
    <style>
      body {
        background-color: #212529;
        color: #dee2e6;
      }
    </style>
  </head>
  <body>
    <form action="/urls" method="post">
      <div class="mb-3">
        <label class="form-label">Enter url</label>
        <input type="url" name="url" class="form-control">
      </div>
      <input type="submit" class="btn btn-primary" value="Submit">
    </form>
  </body>
</html>