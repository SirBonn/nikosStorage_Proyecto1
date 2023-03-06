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
        <jsp:include page="WEB-INF/pages/commonPages/commonHead.jsp"/>

        <link rel="stylesheet" href="resources/CSS/login.css"  type="text/css" media="all" >

        <title>Iniciar Sesion</title>
    </head>
    <body>

        <%-- Barra de navegacion--%>
        <nav>
            <jsp:include page="WEB-INF/pages/commonPages/navBar.jsp"/>
        </nav>

        <div class="LogInBody" id="LogInBody">

            <form class="formularioLogin needs-validation" id="formularioLogin" method="post" name="user-login" action="/Nikos_Storage/loginServlet"> 
                <h1 class="dispay-3">Iniciar Sesión</h1><br>

                <%-- apartado para pedir credenciales --%>


                <div class="form-group">
                    <input type="text" class="form-control" id="usuario" placeholder="Usuario" name="usuario" required>
                    <div class="valid-feedback">Valid.</div>
                    <div class="invalid-feedback">Please fill out this field.</div>
                </div>
                <div class="form-group">
                    <input type="password" class="form-control" id="password" placeholder="Contraseña" name="password" required>
                    <div class="valid-feedback">Valid.</div>
                    <div class="invalid-feedback">Please fill out this field.</div>
                </div>

                <%-- apartado para validacion --%>
                <input type="submit" value="Entrar" id="Entrar" class="btn btn-outline-dark">
                <input type="button" value="Regresar" id="Regresar" class="btn btn-outline-dark" onclick='location.href = location = "index.jsp"'');>
                <br>

            </form>

        </div>
    </body>
</html>
