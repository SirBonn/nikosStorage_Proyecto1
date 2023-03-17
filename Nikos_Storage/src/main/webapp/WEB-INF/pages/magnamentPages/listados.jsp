<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${not empty Error}">
    <div class="alert alert-danger alert-dismissible">
        <button type="button" class="close" data-dismiss="alert">&times;</button>
        <strong>ERROR!</strong> ${Error}
    </div>
</c:if>

<c:if test="${not empty Exito}">
    <div class="alert alert-success alert-dismissible">
        <button type="button" class="close" data-dismiss="alert">&times;</button>
        <strong>EXITO!</strong> ${Exito}
    </div>
</c:if>


<%--Listado de dependientes--%>

<section id="dependientesList">
    <div class="container-fluid row">
        <div class="col-sm-9 col-md-6 col-lg-8 col-xl-10">
            <div class="card">
                <div class="card-header">
                    <h4>Dependientes</h4>
                </div>
                <table class="table table-striped table-bordered">

                    <thead class="thead-dark">
                        <tr>

                            <th>Codigo</th>
                            <th>Nombre</th>
                            <th>Email</th>
                            <th>Tienda atendida</th>        
                            <th></th>

                        </tr>
                    </thead>

                    <tbody>
                        <c:forEach var="dependiente" items="${dependientes}">
                            <tr>
                                <td>${dependiente.codigo}</td>
                                <td>${dependiente.nombre} ${dependiente.apellido}</td>
                                <td>${dependiente.email}</td>
                                <td>${dependiente.tienda}</td>
                                <td><a href="${pageContext.request.contextPath}/ManageControlServlet?accion=ediarUsuario&codigoUsuario=${dependiente.codigo}&table=DEPENDIENTES"
                                       class="btn btn-secondary">Editar</a></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>


    </div>
</section>
<%--Listado de bodegueros--%>
<section id="bodequerosList">
    <div class="container-fluid row">
        <div class="col-sm-9 col-md-6 col-lg-8 col-xl-10">
            <div class="card">
                <div class="card-header">
                    <h4>Encargados de bodega</h4>
                </div>
                <table class="table table-striped table-bordered">
                    <thead class="thead-dark">
                        <tr>

                            <th>Codigo</th>
                            <th>Nombre</th>
                            <th>Email</th>
                            <th>Tienda atendida</th>        
                            <th></th>

                        </tr>
                    </thead>

                    <tbody>
                        <c:forEach var="bodeguero" items="${bodegueros}">
                            <tr>
                                <td>${bodeguero.codigo}</td>
                                <td>${bodeguero.nombre} ${bodeguero.apellido}</td>
                                <td>${bodeguero.email}</td>
                                <td>${bodeguero.tienda}</td>
                                <td><a href="${pageContext.request.contextPath}/ManageControlServlet?accion=ediarUsuario&codigoUsuario=${bodeguero.codigo}&table=BODEGUEROS"
                                       class="btn btn-secondary"> Editar</a></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</section>

<%--Listado de Supervisores--%>
<section id="supervisoresList">
    <div class="container-fluid row">
        <div class="col-sm-9 col-md-6 col-lg-8 col-xl-10">
            <div class="card">
                <div class="card-header">
                    <h4>Supervisores</h4>
                </div>
                <table class="table table-striped table-bordered">
                    <thead class="thead-dark">
                        <tr>

                            <th>Codigo</th>
                            <th>Nombre</th>
                            <th>Email</th>
                            <th></th>

                        </tr>
                    </thead>

                    <tbody>
                        <c:forEach var="supervisor" items="${supervisores}">
                            <tr>
                                <td>${supervisor.codigo}</td>
                                <td>${supervisor.nombre} ${supervisor.apellido}</td>
                                <td>${supervisor.email}</td>
                                <td><a href="${pageContext.request.contextPath}/ManageControlServlet?accion=ediarUsuario&codigoUsuario=${supervisor.codigo}&table=SUPERVISORES"
                                       class="btn btn-secondary">Editar</a></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</section>

<section id="tiendasList">
    <div class="container-fluid row">
        <div class="col-sm-9 col-md-6 col-lg-8 col-xl-10">
            <div class="card">
                <div class="card-header">
                    <h4>Tiendas</h4>
                </div>
                <table class="table table-striped table-bordered">

                    <thead class="thead-dark">
                        <tr>
                            <th>Codigo</th>
                            <th>Nombre</th>
                            <th>Direccion</th>
                            <th>Tipo</th>     
                            <th></th>        

                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="tienda" items="${tiendas}">
                            <tr>
                                <td>${tienda.codigo_tienda}</td>
                                <td>${tienda.nombre}</td>
                                <td>${tienda.direccion}</td>
                                <td>${tienda.tipo}</td>
                                <td><a href="${pageContext.request.contextPath}/ManageControlServlet?accion=eliminarTienda&codigoTienda=${tienda.codigo_tienda}"
                                       class="btn btn-secondary"> Eliminar</a></td>
                            </tr>
                        </c:forEach>
                    </tbody>

                </table>
            </div>
        </div>


    </div>
</section>