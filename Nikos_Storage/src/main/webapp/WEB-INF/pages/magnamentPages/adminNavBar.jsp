<div class="header bg-primary text-light sticky-top">
    <h1 class="dispay bg-primary">NIKOS ADMINISTRATIVE</h1>
    <nav class="navbar navbar-expand-sm bg-primary navbar-dark ">
        <div class="container-fluid">
            <a class="navbar-brand" href="index.jsp">
                <img src="resources/Visual/Infografias NS.png" height="23" width="42">
            </a> <!-- logo en pequenha escala -->
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#collapsibleNavbar">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="collapsibleNavbar">
                <ul class="navbar-nav">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown">Usuarios</a>
                        <ul class="dropdown-menu">
                            <li><a class="dropdown-item" data-toggle="modal" data-target="#agregarUsuario">Agregar</a></li>
                            <li><a class="dropdown-item" href="#">Editar</a></li>
                            <li><a class="dropdown-item" href="#">Eliminar</a></li>
                        </ul>
                    </li><li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown">Tiendas</a>
                        <ul class="dropdown-menu">
                             <li><a class="dropdown-item" data-toggle="modal" data-target="#agregarTienda">Agregar</a></li>
                            <li><a class="dropdown-item" href="#">Editar</a></li>
                            <li><a class="dropdown-item" href="#">Eliminar</a></li>
                        </ul>
                    </li><li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown">Productos</a>
                        <ul class="dropdown-menu">
                            <li><a class="dropdown-item" href="#">Agregar</a></li>
                            <li><a class="dropdown-item" href="#">Editar</a></li>
                            <li><a class="dropdown-item" href="#">Eliminar</a></li>
                        </ul>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown">Archivos</a>
                        <ul class="dropdown-menu">
                            <li><a class="dropdown-item" data-toggle="modal" data-target="#importarArchivo">Importar</a></li>

                            <li><a class="dropdown-item" href="#">Exportar</a></li>
                        </ul>
                    </li>
                </ul>
            </div>
            <hr>
        </div>

    </nav>
</div>

<jsp:include page="../magnamentPages/manejo-controlador.jsp"/>