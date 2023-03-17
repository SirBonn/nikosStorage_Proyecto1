<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <title>gestion administrativa</title>
        <jsp:include page="../commonPages/commonHead.jsp"/>
    </head>
    <body>
        <jsp:include page="../magnamentPages/adminNavBar.jsp"/>
        <form action="${pageContext.request.contextPath}/ManageControlServlet?accion=actualizarUsuario&codigoUsuario=${usrEditable.codigo}&tableUsr=${usrTable}" 
              method="POST">

            <secion id="formEdit" >
                <div class="container">
                    <div class="row">
                        <div class="col">
                            <div class="card ">
                                <div class="card-header">
                                    <div class="col-xl-3">
                                    </div>
                                    <h4>Editar Cliente</h4>
                                </div>
                                <div class="card-body">
                                    <div class="form-group">
                                        <label for="nombre">Nombre</label>
                                        <input type="text" class="form-control" name="nombre" required value="${usrEditable.nombre}">
                                    </div>
                                    <div class="form-group">
                                        <label for="apellido">Apellido</label>
                                        <input type="text" class="form-control" name="apellido" required value="${usrEditable.apellido}">
                                    </div>
                                    <div class="form-group">
                                        <label for="email">Email</label>
                                        <input type="email" class="form-control" name="email" required value="${usrEditable.email}">
                                    </div>
                                    <div class="form-group">
                                        <label for="telefono">Usuario</label>
                                        <input type="text" class="form-control" name="username" required value="${usrEditable.nickName}">
                                    </div>
                                </div>

                                <div class="container">
                                    <div class="row">
                                        <div class="col-sm-6 ">
                                            <label for="nombre" class="form-label">Tipo de Usuario</label>
                                            <select type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown" id="tipoUsuario" name="tipoUsuario" required>
                                                <option value="${usrTable}">Dependiente de tienda</option>
                                                <option value="DEPENDIENTES">Dependiente de tienda</option>
                                                <option value="BODEGUEROS">Encargado de bodega</option>
                                                <option value="SUPERVISORES">Supervisor</option>
                                            </select>
                                        </div>
                                        <div class="col-sm-6 ">
                                            <label for="nombre" class="form-label">Tienda correspondiente</label>
                                            <select type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown" id="tienda" name="tienda" required>
                                                <option value="${usrEditable.tienda}">${usrEditable.tienda}</option>
                                                <c:forEach var="tienda" items="${tiendas}">
                                                    <tr>
                                                    <option value ="${tienda.codigo_tienda}">${tienda.codigo_tienda}</option>
                                                    </tr>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <button class="btn btn-primary" type="submit"> Agregar </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </secion>
                                                
            <section id="actions" class="py-4 bg-light">
                <div class="container">
                    <div class="row">
                        <div class="col-xl-4">
                            <a href="${pageContext.request.contextPath}/ManageControlServlet" class="btn btn-light btn-block">
                                Regresar
                            </a>
                        </div>
                        <div class="col-xl-4">
                            <button type="submit" class="btn btn-success btn-block">
                                Guardar Cambios
                            </button>
                        </div>
                        <div class="col-xl-4">
                            <a href="${pageContext.request.contextPath}/ManageControlServlet?accion=eliminarUsuario&codigoUsuario=${usrEditable.codigo}&tableUsr=${usrTable}" 
                               class="btn btn-danger btn-block">
                                Eliminar a ${usrEditable.nombre}
                            </a>
                        </div>
                    </div>
                </div>
            </section>
        </form>
    </body>

</html>