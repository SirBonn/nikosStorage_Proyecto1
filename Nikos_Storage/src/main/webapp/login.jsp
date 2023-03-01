<%-- 
    Document   : login
    Created on : 26 feb. 2023, 19:53:46
    Author     : sirbon
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">        
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
        <link rel="stylesheet" href="resources/CSS/login.css"  type="text/css" media="all" >

        <title>Iniciar Sesion</title>
    </head>
    <body>

        <nav id="navMenu"></nav>
        <script src="resources/JavaScript/menu.js"></script>
        <script>
            var menuContainer = document.getElementById("navMenu");
            menuContainer.appendChild(generarMenu());
        </script>

        <div class="LogInBody" id="LogInBody">
            <form class="formularioLogin" id=""formularioLogin method="post" name="user-login" action="/Nikos_Storage/loginServlet"> 
                <h1 class="dispay-3">Iniciar Sesión</h1><br>

                <%-- apartado para pedir credenciales --%>
                <div class="row">
                    <div class="texto">
                        <input type="text" class="form-control" placeholder="Usuario" name="usuario"><br>
                    </div>
                    <div class="texto">
                        <input type="password" class="form-control" placeholder="Contraseña" name="password"><br>
                    </div>
                </div>

                <%-- apartado para validacion --%>
                <input type="submit" value="Entrar" id="Entrar" class="btn btn-outline-dark">
                <input type="button" value="Regresar" id="Regresar" class="btn btn-outline-dark" onclick='location.href = location = "index.html"'');>
                <br>

            </form>
        </div>
    </body>
</html>
