@import gg.jte.Content
@import hexlet.code.dto.BasePage
@import hexlet.code.util.NamedRoutes
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
      nav {
        background-color: #adb5bd;
      }
      footer {
        background-color: #adb5bd;
      }
    </style>
  </head>
  <body class="d-flex flex-column min-vh-100">
    <nav class="navbar navbar-expand-lg">
      <div class="container">
        <a class="navbar-brand p-0" href="${NamedRoutes.rootPath()}">Анализатор страниц</a>
        <button class="navbar-toggler"
                type="button"
                data-bs-toggle="collapse"
                data-bs-target="#navbarNav"
                aria-controls="navbarNav"
                aria-expanded="false"
                aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
          <ul class="navbar-nav">
            <li class="nav-item">
              <a class="nav-link active" aria-current="page" href="${NamedRoutes.rootPath()}">Home</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="${NamedRoutes.urlsPath()}">Added URLs</a>
            </li>
          </ul>
        </div>
      </div>
    </nav>
    <main class="flex-grow-1 bg-light">
      
      @if(basePage != null && basePage.getFlash() != null)
        <div class="container my-4">
          @if(basePage.getFlashType().equals("success"))
            <div class="alert alert-success" role="alert">
              ${basePage.getFlash()}
            </div>
          @else
            <div class="alert alert-danger" role="alert">
              ${basePage.getFlash()}
            </div>
          @endif
        </div>
      @endif
      <section class="w-100">
        ${content}
      </section>
    </main>
    <footer class="footer border-top py-3">
      <div class="container x-1">
        <div class="text-center">
          this educational project is available on
          <a href="https://github.com/pro-vitaliy" target="_blank">Github</a>
        </div>
      </div>
    </footer>
  </body>
</html>