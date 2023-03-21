/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webServlets;

import dataBase.*;
import dominio.*;

import java.io.IOException;
import java.util.Iterator;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author sirbon
 */
@WebServlet("/storeControlServlet")
@javax.servlet.annotation.MultipartConfig
public class storeControlServlet extends HttpServlet {

    private int pdNext;
    private int tiendaKey;
    private int devNext;
    private int inNext;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String accion = req.getParameter("accion");

        if (accion != null) {

            switch (accion) {

                case "solicitarDevolucion":
                    this.solicitarDevolucion(req, resp);
                    break;

            }

        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String accion = req.getParameter("accion");

        if (accion != null) {

            switch (accion) {
                case "agregarAlPedido":
                    this.agregarProducto(req, resp);
                    break;
                case "cerrarSesion":
                    this.cerrarSesion(req, resp);
                    break;
                case "crearPedido":
                    this.crearPedido(req, resp);
                    break;
                case "limpiarLista":
                    this.limpiarLista(req, resp);
                    break;
                case "verPedido":
                    this.listarPedido(req, resp, false);
                    break;
                case "verEnvio":
                    this.listarPedido(req, resp, true);
                    break;
                case "reportarProducto":
                    this.reportarProducto(req, resp);
                    break;
                case "recibirProductos":
                    this.recibirProductos(req, resp);
                    break;
            }

        } else {
            this.listarElementos(req, resp);

        }

    }

    private void listarElementos(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            this.pdNext = (new PedidoDAO().listarPedidos().size());
            this.devNext = new DevolucionDAO().listarDevoluciones().size();
            this.inNext = new IncidenciaDAO().listarIncidencias().size();
            this.setTienda(req, resp);
            List<Usuario> bodeguerosDisponibles = new UsuarioDAO().listarUsuariosTienda(tiendaKey);
            req.setAttribute("bodegueros", bodeguerosDisponibles);
            List<Incidencia> incidencias = new IncidenciaDAO().listarIncidencias();
            req.setAttribute("incidencias", incidencias);
            List<Envio> envios = new EnvioDAO().listarEnvios(tiendaKey);
            req.setAttribute("envios", envios);
            List<Pedido> pedidos = new PedidoDAO().listarPedidos(tiendaKey);
            req.setAttribute("pedidos", pedidos);
            List<Devolucion> devoluciones = new DevolucionDAO().listarDevoluciones();
            req.setAttribute("devoluciones", devoluciones);
            List<Producto> productos = new ProductoDAO().listarProductos();
            req.setAttribute("productos", productos);
            req.getRequestDispatcher("/WEB-INF/pages/storePages/store.jsp").forward(req, resp);

        } catch (IOException | ServletException e) {
            e.printStackTrace(System.out);
            // req.getRequestDispatcher("/WEB-INF/pages/storePages/store.jsp").forward(req, resp);

            // this.cerrarSesion(req, resp);
        }

    }

    private void setTienda(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {

            HttpSession session = req.getSession();

            if (session != null && session.getAttribute("dependiente") != null) {
                Usuario usuario = (Usuario) session.getAttribute("dependiente");
                Tienda tienda = new TiendaDAO().buscarTienda(new Tienda(usuario.getTienda()));
                this.tiendaKey = tienda.getCodigo_tienda();
                session.setAttribute("tienda", tienda);
                List<Producto> productosCatalogo = new CatalogoDAO().listarCatalogo(tienda);
                if (tienda.getCatalogo() == null) {
                    tienda.crearCatalogo();
                }
                if (session.getAttribute("pedido") == null) {
                    Pedido pedido = new Pedido(this.pdNext, usuario, tienda);
                    pedido.crearLista();
                    tienda.setPedido(pedido);
                    session.setAttribute("pedido", tienda.getPedido());
                }
                Pedido pedido = (Pedido) session.getAttribute("pedido");
                tienda.setPedido(pedido);
                List<Producto> productosPedido = pedido.getListadoProductos().getListadoProductos();
                session.setAttribute("listadoPedido", productosPedido);

                for (Producto producto : productosCatalogo) {
                    tienda.agregarAlCatalogo(producto);
                }

                req.setAttribute("catalogo", productosCatalogo);

            } else {
                this.cerrarSesion(req, resp);
            }

        } catch (IOException | ServletException e) {
            e.printStackTrace(System.out);
            System.out.println("ocurrio un error debido a " + e);
            getServletContext().setAttribute("Error", "ocurrio un error debido a " + e);
            //request.getRequestDispatcher("/gestion-administrativa.jsp").forward(request, response);
            req.getRequestDispatcher("gestion-tienda.jsp").forward(req, resp);
        }

    }

    private void listarPedido(HttpServletRequest req, HttpServletResponse resp, boolean isEnvio) throws ServletException, IOException {

        try {
            Pedido pedido;
            Envio envio;
            List<Producto> productosRegistrados = null;
            int codigo = Integer.parseInt(req.getParameter("codigoPedido"));

            if (isEnvio) {
                envio = new EnvioDAO().buscarEnvio(new Envio(codigo));
                req.getSession().setAttribute("envioRegistrado", envio);
                productosRegistrados = new ListadoProductosDAO().listarProductos(envio.getPedidoEnviado());
                req.getSession().setAttribute("productosEnvRegistrados", productosRegistrados);

            } else {
                pedido = new PedidoDAO().buscarPedido(new Pedido(codigo));
                req.getSession().setAttribute("pedidoRegistrado", pedido);
                productosRegistrados = new ListadoProductosDAO().listarProductos(pedido);
                req.getSession().setAttribute("productosPdRegistrados", productosRegistrados);

            }

            req.getRequestDispatcher("gestion-tienda.jsp").forward(req, resp);

        } catch (Exception e) {

            e.printStackTrace(System.out);
            System.out.println("ocurrio un error debido a " + e);
            getServletContext().setAttribute("Error", "ocurrio un error debido a " + e);
            //request.getRequestDispatcher("/gestion-administrativa.jsp").forward(request, response);
            req.getRequestDispatcher("gestion-tienda.jsp").forward(req, resp);
        }
    }

    private void agregarProducto(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            boolean existPrd = false;

            ProductoDAO productoDAO = new ProductoDAO();
            Pedido pedido = (Pedido) req.getSession().getAttribute("pedido");

            int codigoProducto = Integer.parseInt(req.getParameter("codigoProducto"));
            Producto producto = productoDAO.buscarProducto(new Producto(codigoProducto));
            pedido.setCostoPedido(pedido.getCostoPedido() + producto.getPrecioVenta());

            Iterator<Producto> iterador = pedido.getListadoProductos().getListadoProductos().iterator();
            while (iterador.hasNext()) {

                Producto productoIt = iterador.next();

                if (productoIt.getCodigo() == producto.getCodigo()) {
                    productoIt.setCantidad(productoIt.getCantidad() + 1);
                    existPrd = true;
                }
            }

            if (!existPrd) {
                producto.setCantidad(1);
                pedido.agregarAlListado(producto);
            }

            if (productoDAO.getInforme().equals("")) {
                getServletContext().setAttribute("Exito", "Se agrego el producto al pedido ");
            } else {
                getServletContext().setAttribute("Error", "ocurrio un error debido a " + productoDAO.getInforme());
            }
            req.getRequestDispatcher("gestion-tienda.jsp").forward(req, resp);

        } catch (IOException | NumberFormatException | ServletException e) {

            System.out.println("ocurrio un error debido a " + e);
            e.printStackTrace(System.out);
            getServletContext().setAttribute("Error", "ocurrio un error debido a " + e);
            //request.getRequestDispatcher("/gestion-administrativa.jsp").forward(request, response);
            req.getRequestDispatcher("gestion-tienda.jsp").forward(req, resp);
        }

    }

    private void crearPedido(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Tienda tienda = (Tienda) req.getSession().getAttribute("tienda");
            Pedido pedido = (Pedido) req.getSession().getAttribute("pedido");
            Usuario dependienteSolicitante = (Usuario) req.getSession().getAttribute("dependiente");

            pedido.setFechaPedido(pedido.setNowDate());
            pedido.setDependiente(dependienteSolicitante);

            int rows = new PedidoDAO().insertarPedido(pedido);

            if (rows != 0) {
                int rowsA = new ListadoProductosDAO().insertarProducto(pedido.getListadoProductos());
                int rowsB = new CatalogoDAO().actualizarProducto(tienda.getCodigo_tienda(), pedido.getListadoProductos().getListadoProductos());
                getServletContext().setAttribute("Exito", "Se agrego el producto al pedido ");
                limpiarLista(req, resp);
                pedido = new Pedido((this.pdNext + 1), dependienteSolicitante, tienda);
                pedido.crearLista();

                req.getSession().setAttribute("pedido", pedido);

            } else {
                getServletContext().setAttribute("Error", "ocurrio un error");
            }
            req.getRequestDispatcher("gestion-tienda.jsp").forward(req, resp);

        } catch (IOException | ServletException e) {
            System.out.println("ocurrio un error debido a " + e);
            e.printStackTrace(System.out);
            getServletContext().setAttribute("Error", "ocurrio un error debido a " + e);
            //request.getRequestDispatcher("/gestion-administrativa.jsp").forward(request, response);
            req.getRequestDispatcher("gestion-tienda.jsp").forward(req, resp);
        }

    }

    private void reportarProducto(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String isDev = req.getParameter("isDev");
        int codigoEnvio = Integer.parseInt(req.getParameter("codigoEnvio"));
        Envio envio = new EnvioDAO().buscarEnvio(new Envio(codigoEnvio));
        int productoRep = Integer.parseInt(req.getParameter("productoRep"));
        Producto producto = new ListadoProductosDAO().obtenerProducto(envio.getPedidoEnviado(), new Producto(productoRep));
        req.setAttribute("productoRep", producto);
        req.setAttribute("envio", envio);
        List<Usuario> bodeguerosDisponibles = new UsuarioDAO().listarUsuariosTienda(tiendaKey);
        req.setAttribute("bodegueros", bodeguerosDisponibles);
        if (isDev.equals("true")) {

            req.setAttribute("isDev", true);

        } else {
            int nuevaInc = this.inNext + 1;
            Incidencia incidencia = new Incidencia(nuevaInc);
            incidencia.setEnvioDevuelto(envio);
            incidencia.setFechaIncidencia(incidencia.setNowDate());
            incidencia.setEstadoIncidencia("ACTIVA");

            req.setAttribute("incidencia", incidencia);
            req.setAttribute("isDev", false);
        }

        String jspEdit = "/WEB-INF/pages/storePages/reportarEnvio.jsp";
        req.getRequestDispatcher(jspEdit).forward(req, resp);
    }

    private void recibirProductos(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {

            int codigoEnvio = Integer.parseInt(req.getParameter("codigoEnvio"));
            Envio envio = new EnvioDAO().buscarEnvio(new Envio(codigoEnvio));
            envio.setFechaRecepcion(envio.setNowDate());
            envio.setEstado("RECIBIDO");
            int rows = new EnvioDAO().actualizarEnvio(envio);
            envio.getPedidoEnviado().setEstadoPedido("COMPLETADO");
            int rowsA = new PedidoDAO().actualizarPedidos(envio.getPedidoEnviado());

            getServletContext().setAttribute("Exito", "Se recibio el pedido");
            req.getRequestDispatcher("gestion-tienda.jsp").forward(req, resp);

        } catch (NumberFormatException e) {
            e.printStackTrace(System.out);
        }

    }

    private void solicitarDevolucion(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DevolucionDAO devolucionDAO = new DevolucionDAO();

        try {
            int codigoEnvio = Integer.parseInt(req.getParameter("codigoEnvio"));
            Envio envio = new EnvioDAO().buscarEnvio(new Envio(codigoEnvio));
            int codigoProducto = Integer.parseInt(req.getParameter("codigoProducto"));
            Producto prodcuto = new ListadoProductosDAO().obtenerProducto(envio.getPedidoEnviado(), new Producto(codigoProducto));
            String motivoDevolucion = req.getParameter("motivoDevolucion");
            int cantidadDevuelta = Integer.parseInt(req.getParameter("cantidad"));
            prodcuto.setCantidad(cantidadDevuelta);
            int nuevaDev = this.devNext;
            Devolucion devolucion = new Devolucion(nuevaDev);
            int codigoEncargado = Integer.parseInt(req.getParameter("encargado"));
            Usuario encargado = new UsuarioDAO().buscarUsuario(new Usuario(codigoEncargado), "BODEGUEROS");
            ReclamoDevolucion reclamoDevolucion = new ReclamoDevolucion(devolucion.getCodigoDevolucion(), motivoDevolucion, prodcuto);

            devolucion.setTotalDevuelto(reclamoDevolucion.getProductoDevuelto().getTotal());
            devolucion.setFechaDevolucion(devolucion.setNowDate());
            devolucion.setEstadoDevolucion("ACTIVA");
            devolucion.setEncargado(encargado);
            devolucion.setEnvioDevuelto(envio);
            devolucion.setReclamoDevolucion(reclamoDevolucion);

            int rowsAffected = devolucionDAO.insertarDevolucion(devolucion);

            if (rowsAffected != 0) {
                int rowsAff = new ReclamoDevolucionDAO().insertarReclamo(reclamoDevolucion);
                getServletContext().setAttribute("Exito", "Se envio la solicitud con exito");
            } else {
                getServletContext().setAttribute("Error", "ocurrio un error al procesar la solicitud debido a " + devolucionDAO.getInforme());
            }

            req.getRequestDispatcher("gestion-tienda.jsp").forward(req, resp);

        } catch (IOException | NumberFormatException | ServletException e) {
            e.printStackTrace(System.out);
            getServletContext().setAttribute("Error", "ocurrio un error al procesar la solicitud debido a " + e);
            req.getRequestDispatcher("gestion-tienda.jsp").forward(req, resp);

        }

    }

    private void limpiarLista(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            HttpSession session = req.getSession();
            Pedido pedido = (Pedido) req.getSession().getAttribute("pedido");
            pedido.crearLista();
            List<Producto> productosPedido = pedido.getListadoProductos().getListadoProductos();
            session.setAttribute("listadoPedido", productosPedido);

            getServletContext().setAttribute("Exito", "Se limpio la lista de productos ");
            req.getRequestDispatcher("gestion-tienda.jsp").forward(req, resp);

        } catch (IOException | ServletException e) {

            getServletContext().setAttribute("Error", "ocurrio un error debido a " + e);
            req.getRequestDispatcher("gestion-tienda.jsp").forward(req, resp);

        }

    }

    private void cerrarSesion(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        getServletContext().setAttribute("Error", null);
        getServletContext().setAttribute("Exito", null);
        resp.sendRedirect(req.getContextPath() + "/index.jsp");
    }

}
