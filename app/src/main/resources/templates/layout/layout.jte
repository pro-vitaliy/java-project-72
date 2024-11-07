@import gg.jte.Content
@import hexlet.code.dto.BasePage
@param BasePage basePage = null
@param Content content


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
    <div>
      @if(basePage != null && basePage.getFlash() != null)
        <p>${basePage.getFlash()}</p>
      @endif
    </div>
    ${content}
  </body>
</html>