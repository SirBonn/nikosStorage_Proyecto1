function generarMenu() {
    const navbar = document.createElement('nav');
    navbar.classList.add('navbar', 'navbar-expand-sm', 'bg-danger', 'navbar-dark', 'sticky-top');

    const container = document.createElement('div');
    container.classList.add('container-fluid');

    const logo = document.createElement('a');
    logo.classList.add('navbar-brand');
    logo.setAttribute('href', 'index.html');
    logo.textContent = 'Logo';
    container.appendChild(logo);
    const containerFluid = document.createElement("div");
    containerFluid.classList.add("container-fluid");

    const navbarToggler = document.createElement('button');
    navbarToggler.classList.add('navbar-toggler');
    navbarToggler.type = 'button';
    navbarToggler.setAttribute('data-bs-toggle', 'collapse');
    navbarToggler.setAttribute('data-bs-toggler', '#collapsibleNavBar');
    const togglerIcon = document.createElement('span');
    togglerIcon.classList.add('navbar-togler-icon');
    navbarToggler.appendChild(togglerIcon);
    containerFluid.appendChild(navbarToggler);
    //problema con el button-collapse

    const collapseNavbar = document.createElement('div');
    collapseNavbar.classList.add('collapse', 'navbar-collapse');
    collapseNavbar.setAttribute('id', 'collapsibleNavbar');

    const navList = document.createElement('ul');
    navList.classList.add('navbar-nav');

    const productos = document.createElement('li');
    productos.classList.add('nav-item');
    const productosLink = document.createElement('a');
    productosLink.classList.add('nav-link');
    productosLink.setAttribute('href', 'productos-niko.jsp');
    productosLink.textContent = 'Nuestros Productos';
    productos.appendChild(productosLink);
    navList.appendChild(productos);

    const tiendas = document.createElement('li');
    tiendas.classList.add('nav-item');
    const tiendasLink = document.createElement('a');
    tiendasLink.classList.add('nav-link');
    tiendasLink.setAttribute('href', 'tiendas-niko.jsp');
    tiendasLink.textContent = 'Nuestras tiendas';
    tiendas.appendChild(tiendasLink);
    navList.appendChild(tiendas);

    const iniciarSesion = document.createElement('li');
    iniciarSesion.classList.add('nav-item');
    const sessionLink = document.createElement('a');
    sessionLink.classList.add('nav-link');
    sessionLink.setAttribute('href', 'login.jsp');
    sessionLink.textContent = 'Iniciar Sesion';
    iniciarSesion.appendChild(sessionLink);
    navList.appendChild(iniciarSesion);


    const acercaDe = document.createElement('li');
    acercaDe.classList.add('nav-item', 'dropdown');
    const acercaDeLink = document.createElement('a');
    acercaDeLink.classList.add('nav-link', 'dropdown-toggle');
    acercaDeLink.setAttribute('href', '#');
    acercaDeLink.setAttribute('role', 'button');
    acercaDeLink.setAttribute('data-bs-toggle', 'dropdown');
    acercaDeLink.textContent = 'Acerca de Nosotros';
    acercaDe.appendChild(acercaDeLink);

    const dropdownMenu = document.createElement('ul');
    dropdownMenu.classList.add('dropdown-menu');

    const vision = document.createElement('li');
    const visionLink = document.createElement('a');
    visionLink.classList.add('dropdown-item');
    visionLink.setAttribute('href', '#');
    visionLink.textContent = 'Vision';
    vision.appendChild(visionLink);
    dropdownMenu.appendChild(vision);

    const mision = document.createElement('li');
    const misionLink = document.createElement('a');
    misionLink.classList.add('dropdown-item');
    misionLink.setAttribute('href', '#');
    misionLink.textContent = 'Mision';
    mision.appendChild(misionLink);
    dropdownMenu.appendChild(mision);

    const contactos = document.createElement('li');
    const contactosLink = document.createElement('a');
    contactosLink.classList.add('dropdown-item');
    contactosLink.setAttribute('href', '#');
    contactosLink.textContent = 'Contactos';
    contactos.appendChild(contactosLink);
    dropdownMenu.appendChild(contactos);

    acercaDe.appendChild(dropdownMenu);
    navList.appendChild(acercaDe);

    collapseNavbar.appendChild(navList);
    container.appendChild(collapseNavbar);
    navbar.appendChild(container);

    const hr = document.createElement('hr');
    document.body.insertBefore(hr, document.querySelector('.container-fluid'));

    document.body.insertBefore(navbar, hr);


}


