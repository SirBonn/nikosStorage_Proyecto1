<%-- 
    Document   : tiendasNIko
    Created on : 26 feb. 2023, 16:53:22
    Author     : sirbon
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
        <title>Tiendas Niko</title>
    </head>
    <body>

        <nav id="navMenu"></nav>
        <script src="resources/JavaScript/menu.js"></script>
        <script>
            var menuContainer = document.getElementById("navMenu");
            menuContainer.appendChild(generarMenu());
        </script>

        <h1>PAGINA DE TIENDAS</h1>

    </body>
</html>
