<div class="container-fluid bg-success text-dark sticky-top ">
    <div class="container-fluid">
        <div class="row">
            <h1 class="bg-success">Bodega ${tienda.nombre}</h1>
        </div>
    </div>

    <nav class="navbar navbar-expand-sm bg-success navbar-dark ">
        <div class="container-fluid">
            <a class="navbar-brand" href="#">
                <img src="resources/Visual/Infografias NS.png" height="23" width="42">
            </a> <!-- logo en pequenha escala -->
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#collapsibleNavbar">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="collapsibleNavbar">
                <ul class="navbar-nav">
                    <!--Agregar usuario -->
                    <li class="nav-item">
                        <a class="nav-link text-dark" data-toggle="modal" data-target="#">Agregar Pedido</a>
                    </li>
                    <!-- agregar tienda -->
                    <li class="nav-item">
                        <a class="nav-link text-dark" data-toggle="modal" data-target="#insertarProducto">Agregar Producto</a>
                    </li> 
                    <!-- apartado de reportes -->
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle  text-dark" href="#" id="navbardrop" data-toggle="dropdown">Reportes</a>
                        <ul class="dropdown-menu">
                            <li><a class="dropdown-item" href="#">Tiendas con mas pedidos</a></li>
                            <li><a class="dropdown-item" href="#">Usuarios con mas envios</a></li>
                            <li><a class="dropdown-item" href="#">Usuarios con mas pedidos</a></li>
                            <li><a class="dropdown-item" href="#">Productos mas solicitados</a></li>
                            <li><a class="dropdown-item" href="#">Productos mas problematicos</a></li>

                        </ul>
                    </li>

                    <li class="nav-item">
                        <span>
                            <a href="${pageContext.request.contextPath}/storeControlServlet?accion=cerrarSesion"
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
