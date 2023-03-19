/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webServlets;

import dataBase.*;
import dominio.*;

import fileMagnament.FileManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 *
 * @author sirbon
 */
@WebServlet("/storeControlServlet")
@javax.servlet.annotation.MultipartConfig
public class storeControlServlet extends HttpServlet {

    private int pdNext;
    private int tiendaKey;
    private int enNext;
    private int inNext;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

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

            }

        } else {
            this.listarElementos(req, resp);

        }

    }

    private void listarElementos(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            this.pdNext = (new PedidoDAO().listarPedidos().size());
            this.setTienda(req, resp);
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

        } catch (Exception e) {
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
            pedido.setDependienteSolicitante(dependienteSolicitante);

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
