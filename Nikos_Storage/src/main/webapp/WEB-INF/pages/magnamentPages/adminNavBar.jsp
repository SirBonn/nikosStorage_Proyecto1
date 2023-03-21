<div class="header bg-primary text-light sticky-top">
    <h1 class="dispay bg-primary">NIKOS ADMINISTRATIVE</h1>
    <nav class="navbar navbar-expand-sm bg-primary navbar-dark ">
        <div class="container-fluid">
            <a class="navbar-brand" href="">
                <img src="resources/Visual/Infografias NS.png" height="23" width="42">
            </a> <!-- logo en pequenha escala -->
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#collapsibleNavbar">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="collapsibleNavbar">
                <ul class="navbar-nav">
                    <!--Agregar usuario -->
                    <li class="nav-item">
                        <a class="nav-link" data-toggle="modal" data-target="#agregarUsuario">Agregar Usuario</a>
                    </li>
                    <!-- agregar tienda -->
                    <li class="nav-item">
                        <a class="nav-link" data-toggle="modal" data-target="#agregarTienda">Agregar Tienda</a>
                    </li> 
                    <!-- apartado de reportes -->
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown">Reportes</a>
                        <ul class="dropdown-menu">
                            <li><a class="dropdown-item" data-toggle="modal" data-target="#reporteTiendasPedidos">Tiendas con mas pedidos</a></li>
                            <li><a class="dropdown-item" data-toggle="modal" data-target="#reporteTiendasEnvios">Tiendas con mas envios</a></li>
                            <li><a class="dropdown-item" data-toggle="modal" data-target="#topUsuariosPedidos">Usuarios con mas pedidos</a></li>
                            <li><a class="dropdown-item" data-toggle="modal" data-target="#topProductos">Productos mas solicitados</a></li>
                            <li><a class="dropdown-item" data-toggle="modal" data-target="#topProductosDev">Productos mas devueltos</a></li>
                            <li><a class="dropdown-item" data-toggle="modal" data-target="#topProductosInc">Productos mas incidentes</a></li>


                        </ul>
                    </li>

                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown">Archivos</a>
                        <ul class="dropdown-menu">
                            <li><a class="dropdown-item" data-toggle="modal" data-target="#importarArchivo">Importar</a></li>

                            <li><a class="dropdown-item" href="#">Exportar</a></li>
                        </ul>
                    </li>
                    <li class="nav-item">
                        <span>
                            <a href="${pageContext.request.contextPath}/ManageControlServlet?accion=cerrarSesion"
                               class="btn btn-secondary" >
                                Cerrar sesion</a>
                        </span>
                    </li>
                </ul>
            </div>
            <hr>
        </div>

    </nav>
</div>

<jsp:include page="../magnamentPages/manejo-controlador.jsp"/>