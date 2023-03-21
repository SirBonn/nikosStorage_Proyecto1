<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%--Carga de archivos -Modal--%>
<div class="modal fade" id="importarArchivo" role="dialog">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header bg-info text-white">
                <h5 class="modal-tittle">Importar archivo</h5>
                <button class="close" data-dismiss="modal">
                    <span>&times;</span>
                </button>
            </div>
            <div >
                <form class="" method="post" enctype="multipart/form-data" action="${pageContext.request.contextPath}/ManageControlServlet?accion=cargarArchivos"> 
                    <div class="modal-body">
                        <input type="file" class="form-control-file border" id="JSONfile" name="JSONfile" required>
                    </div>
                    <div class="modal-footer">
                        <button class="btn btn-primary" type="submit"> Cargar</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<%--Carga agregar usuarios -Modal--%>
<div class="modal fade" id="agregarUsuario" role="dialog">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header bg-info text-white">
                <h5 class="modal-tittle">Agregar Usuario</h5>
                <button class="close" data-dismiss="modal">
                    <span>&times;</span>
                </button>
            </div>
            <div>
                <div class="modal-body">
                    <h5 class="card-title">Registro</h5>
                    <form class="" method="post" enctype="multipart/form-data" action="${pageContext.request.contextPath}/ManageControlServlet?accion=agregarUsuario"> 
                        <div class="mb-3">
                            <label for="nombre" class="form-label">Nombre</label>
                            <input type="text" class="form-control" id="nombre" name="nombre" maxlength="25" required>
                        </div>
                        <div class="mb-3">
                            <label for="nombre" class="form-label">Apellido</label>
                            <input type="text" class="form-control" id="apellido" name="apellido" maxlength="15">
                        </div>
                        <div class="mb-3">
                            <label for="username" class="form-label">Username</label>
                            <input type="text" class="form-control" id="username" name="username" maxlength="15" required>
                        </div>
                        <div class="mb-3">
                            <label for="email" class="form-label">Email</label>
                            <input type="email" class="form-control" id="email" name="email" maxlength="45"required>
                        </div>
                        <div class="mb-3">
                            <label for="password" class="form-label">Contraseña</label>
                            <input type="password" class="form-control" id="password" name="password" required>
                        </div>
                        <div class="mb-3">
                            <label for="password-confirm" class="form-label">Confirmar Contraseña</label>
                            <input type="password" class="form-control" id="passwordConfirm" required>
                        </div>



                        <div class="container">
                            <div class="row">
                                <div class="col-sm-6 ">
                                    <label for="nombre" class="form-label">Tipo de Usuario</label>
                                    <select type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown" id="tipoUsuario" name="tipoUsuario" required>
                                        <option>Selecciona</option>
                                        <option value="DEPENDIENTES">Dependiente de tienda</option>
                                        <option value="BODEGUEROS">Encargado de bodega</option>
                                        <option value="SUPERVISORES">Supervisor</option>
                                    </select>
                                </div>
                                <div class="col-sm-6 ">
                                    <label for="nombre" class="form-label">Tienda correspondiente</label>
                                    <select type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown" id="tienda" name="tienda" required>
                                        <option>Selecciona</option>
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

                            <c:if test="${passwordConfirm == password}">
                                <button class="btn btn-primary" type="submit"> Agregar </button>
                            </c:if>
                            <c:if test="${passwordConfirm != password}">
                                <label class="text-danger">Verifica confirma la contraseña</label>
                                <button class="btn btn-danger" disabled type="submit"> Agregar </button>    
                            </c:if>

                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<%--Agregar tiendas -Modal--%>
<div class="modal fade" id="agregarTienda" role="dialog">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header bg-info text-white">
                <h5 class="modal-tittle">Agregar Tienda</h5>
                <button class="close" data-dismiss="modal">
                    <span>&times;</span>
                </button>
            </div>
            <div>
                <div class="modal-body">
                    <h5 class="card-title">Registro</h5>

                    <form class="" method="post" enctype="multipart/form-data" action="${pageContext.request.contextPath}/ManageControlServlet?accion=agregarTienda"> 
                        <div class="mb-3">
                            <label for="nombre" class="form-label">Nombre de la Tienda</label>
                            <input type="text" class="form-control" id="nombre" name="nombre" maxlength="40" required>
                        </div>
                        <div class="mb-3">
                            <label for="nombre" class="form-label">Direccion de la Tienda</label>
                            <input type="text" class="form-control" id="direccion" name="direccion" maxlength="40" >
                        </div>
                        <div class="mb-3 form-check ">
                            <label for="tipoTienda" class="form-check-label"></label>
                            <input type="checkbox" class="form-check-input" value="Supervisada" id="tipoTienda" name="tipoTienda" >Tienda Supervisada
                        </div>

                        <div class="modal-footer">
                            <button class="btn btn-primary" type="submit"> Agregar </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<%--Reporte tiendas -Modal--%>
<div class="modal fade" id="reporteTiendasPedidos" role="dialog">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header bg-info text-white">
                <h5 class="modal-tittle">Tiendas con Mas pedidos</h5>
                <button class="close" data-dismiss="modal">
                    <span>&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="card">
                    <div class="card-header">
                        <h4>Tiendas</h4>
                    </div>
                    <table class="table table-striped table-bordered">
                        <thead class="thead-dark">
                            <tr>
                                <th>Codigo</th>
                                <th>Nombre</th>
                                <th>Tipo</th>     
                                <th>Cantidad de pedidos</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="tienda" items="${topTndPedidos}">
                                <tr>
                                    <td>${tienda.codigo_tienda}</td>
                                    <td>${tienda.nombre}</td>
                                    <td>${tienda.tipo}</td>
                                    <td>${tienda.cantidad}</td>
                                </tr>
                            </c:forEach>
                        </tbody>

                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="reporteTiendasEnvios" role="dialog">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header bg-info text-white">
                <h5 class="modal-tittle">Tiendas con Mas envios</h5>
                <button class="close" data-dismiss="modal">
                    <span>&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="card">
                    <div class="card-header">
                        <h4>Tiendas</h4>
                    </div>
                    <table class="table table-striped table-bordered">
                        <thead class="thead-dark">
                            <tr>
                                <th>Codigo</th>
                                <th>Nombre</th>
                                <th>Tipo</th>     
                                <th>Cantidad de envios</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="tienda" items="${topTndEnvios}">
                                <tr>
                                    <td>${tienda.codigo_tienda}</td>
                                    <td>${tienda.nombre}</td>
                                    <td>${tienda.tipo}</td>
                                    <td>${tienda.cantidad}</td>
                                </tr>
                            </c:forEach>
                        </tbody>

                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="topUsuariosPedidos" role="dialog">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header bg-info text-white">
                <h5 class="modal-tittle">Usuarios con mas</h5>
                <button class="close" data-dismiss="modal">
                    <span>&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="card">
                    <div class="card-header">
                        <h4>Usuarios</h4>
                    </div>
                    <table class="table table-striped table-bordered">
                        <thead class="thead-dark">
                            <tr>
                                <th>Codigo</th>
                                <th>Nombre</th>
                                <th>Tienda</th>     
                                <th>Cantidad de pedidos</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="usuario" items="${topUsrPedidos}">
                                <tr>
                                    <td>${usuario.codigo}</td>
                                    <td>${usuario.nombre}</td>
                                    <td>${usuario.tienda}</td>
                                    <td>${usuario.cantidad}</td>
                                </tr>
                            </c:forEach>
                        </tbody>

                    </table>
                </div>
            </div>
        </div>
    </div>
</div>


<div class="modal fade" id="topProductos" role="dialog">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header bg-info text-white">
                <h5 class="modal-tittle">Top Productos</h5>
                <button class="close" data-dismiss="modal">
                    <span>&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="card">
                    <div class="card-header">
                        <h4>Productos</h4>
                    </div>
                    <table class="table table-striped table-bordered">
                        <thead class="thead-dark">
                            <tr>
                                <th>Codigo</th>
                                <th>Nombre</th>
                                <th>Costo</th>     
                                <th>Cantidad de pedidos</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="producto" items="${topProductos}">
                                <tr>
                                    <td>${producto.codigo}</td>
                                    <td>${producto.nombre}</td>
                                    <td>${producto.precioVenta}</td>
                                    <td>${producto.cantidad}</td>
                                </tr>
                            </c:forEach>
                        </tbody>

                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="topProductosDev" role="dialog">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header bg-info text-white">
                <h5 class="modal-tittle">Productos mas devueltos</h5>
                <button class="close" data-dismiss="modal">
                    <span>&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="card">
                    <div class="card-header">
                        <h4>Productos</h4>
                    </div>
                    <table class="table table-striped table-bordered">
                        <thead class="thead-dark">
                            <tr>
                                <th>Codigo</th>
                                <th>Nombre</th>
                                <th>Tienda</th>     
                                <th>Cantidad de devoluciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="producto" items="${topProductosDev}">
                                <tr>
                                    <td>${producto.codigo}</td>
                                    <td>${producto.nombre}</td>
                                    <td>${producto.cantidad}</td>
                                </tr>
                            </c:forEach>
                        </tbody>

                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="topProductosInc" role="dialog">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header bg-info text-white">
                <h5 class="modal-tittle">Productos mas incidentes</h5>
                <button class="close" data-dismiss="modal">
                    <span>&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="card">
                    <div class="card-header">
                        <h4>Productos</h4>
                    </div>
                    <table class="table table-striped table-bordered">
                        <thead class="thead-dark">
                            <tr>
                                <th>Codigo</th>
                                <th>Nombre</th>
                                <th>Tienda</th>     
                                <th>Cantidad de incidencias</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="producto" items="${topProductosInc}">
                                <tr>
                                    <td>${producto.codigo}</td>
                                    <td>${producto.nombre}</td>
                                    <td>${producto.cantidad}</td>
                                </tr>
                            </c:forEach>
                        </tbody>

                    </table>
                </div>
            </div>
        </div>
    </div>
</div>