package webServlets;

import dataBase.*;
import dominio.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import java.util.List;
import java.util.ListIterator;

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
@WebServlet("/bodegaControlServlet")
@javax.servlet.annotation.MultipartConfig
public class bodegaControlServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String accion = req.getParameter("accion");

        switch (accion) {
            case "actualizarIncidencia":
                this.registrarSolucion(req, resp);
                break;
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String accion = req.getParameter("accion");

        if (accion != null) {
            switch (accion) {
                case "solucionarIncidencia":
                    this.solucionarIncidencia(req, resp);
                    break;
                case "evaluarDevolucion":
                    this.evaluarDevolucion(req, resp);
                    break;
                case "rechazarPedido":
                    this.rechazarPedido(req, resp);
                    break;
                case "aprobarEnvio":
                    this.aprobarEnvio(req, resp);
                    break;
                case "verPedido":
                    this.listarPedido(req, resp, false);
                    break;
                case "verEnvio":
                    this.listarPedido(req, resp, true);
                    break;
                case "reportarProducto":
                    break;
                case "recibirProductos":
                    break;
            }
        } else {
            this.listarElementos(req, resp);
        }

    }

    private void listarElementos(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Usuario usuario;
        try {
            usuario = (Usuario) session.getAttribute("bodeguero");

            List<Incidencia> incidencias = new IncidenciaDAO().listarIncidencias();
            req.setAttribute("incidencias", incidencias);
            List<Envio> envios = new EnvioDAO().listarEnvios(usuario.getTienda());
            req.setAttribute("envios", envios);
            List<Pedido> pedidos = new PedidoDAO().listarPedidos(usuario.getTienda());
            req.setAttribute("pedidos", pedidos);
            List<Devolucion> devoluciones = new DevolucionDAO().listarDevoluciones();
            req.setAttribute("devoluciones", devoluciones);
            req.getRequestDispatcher("/WEB-INF/pages/bodegaPages/bodega.jsp").forward(req, resp);
        } catch (IOException | ServletException e) {
            e.printStackTrace(System.out);
            this.cerrarSesion(req, resp);
        }
    }

    private void rechazarPedido(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {

            int codigoPedido = Integer.parseInt(req.getParameter("codigoPedido"));
            Pedido pedido = new PedidoDAO().buscarPedido(new Pedido(codigoPedido));
            pedido.setFechaPedido(pedido.setNowDate());
            pedido.setEstadoPedido("RECHAZADO");
            int rowsA = new PedidoDAO().actualizarPedidos(pedido);

            getServletContext().setAttribute("Advertencia", "Se Rechazo el pedido");
            req.getRequestDispatcher("gestion-bodega.jsp").forward(req, resp);

        } catch (NumberFormatException e) {
            e.printStackTrace(System.out);
            req.getRequestDispatcher("gestion-bodega.jsp").forward(req, resp);

        }

    }

    private void evaluarDevolucion(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {

            int codigo = Integer.parseInt(req.getParameter("codigo"));
            String isRechazada = req.getParameter("isRechazada");
            Devolucion devolucion = new DevolucionDAO().buscarDevolucion(new Devolucion(codigo));
            if (isRechazada.equals("true")) {
                devolucion.setEstadoDevolucion("RECHAZADA");

            } else {
                devolucion.setEstadoDevolucion("APROBADA");

            }

            int rowsA = new DevolucionDAO().actualizarDevolucion(devolucion);

            getServletContext().setAttribute("Advertencia", "Se Rechazo la devolucion");
            req.getRequestDispatcher("gestion-bodega.jsp").forward(req, resp);

        } catch (NumberFormatException e) {
            e.printStackTrace(System.out);
            req.getRequestDispatcher("gestion-bodega.jsp").forward(req, resp);

        }

    }

    private void aprobarEnvio(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {

            int codigoPedido = Integer.parseInt(req.getParameter("codigoPedido"));
            Pedido pedido = new PedidoDAO().buscarPedido(new Pedido(codigoPedido));
            Tienda tienda = new TiendaDAO().buscarTienda(new Tienda(pedido.getDependiente().getTienda()));
            Envio envio = new Envio(pedido.setNowDate(), "DESPACHADO", pedido.getCostoPedido(), pedido, tienda, pedido.getListadoProductos());
            int rowsAffected = new EnvioDAO().insertarEnvio(envio);
            if (rowsAffected != 0) {
                pedido.setFechaPedido(pedido.setNowDate());
                pedido.setEstadoPedido("COMPLETADO");
                int rowsA = new PedidoDAO().actualizarPedidos(pedido);
            } else {

            }

            getServletContext().setAttribute("Advertencia", "Se aprobo el pedido y realizo el envio");
            req.getRequestDispatcher("gestion-bodega.jsp").forward(req, resp);

        } catch (NumberFormatException e) {
            e.printStackTrace(System.out);
        }
    }

    private void registrarSolucion(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int codigo = Integer.parseInt(req.getParameter("codigo"));
            Incidencia incidencia = new IncidenciaDAO().buscarIncidencia(new Incidencia(codigo));
            incidencia.setEstadoIncidencia("SOLUCIONADA");
            String solucion = req.getParameter("solucion");
            incidencia.setSolucion(solucion);

            int rows = new IncidenciaDAO().actualizarIncidencia(incidencia);
            req.getSession().setAttribute("incidenciaSolucionable", null);
        } catch (Exception e) {
            e.printStackTrace(System.out);
            System.out.println("ocurrio un error debido a " + e);
            getServletContext().setAttribute("Error", "ocurrio un error debido a " + e);
            //request.getRequestDispatcher("/gestion-administrativa.jsp").forward(request, response);
            req.getRequestDispatcher("gestion-bodega.jsp").forward(req, resp);
        }
    }

    private void solucionarIncidencia(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {

            int codigo = Integer.parseInt(req.getParameter("codigo"));
            Incidencia incidencia = new IncidenciaDAO().buscarIncidencia(new Incidencia(codigo));
            incidencia.setEstadoIncidencia("SOLUCIONADA");
            req.getSession().setAttribute("incidenciaSolucionable", incidencia);
            req.getRequestDispatcher("gestion-bodega.jsp").forward(req, resp);

        } catch (Exception e) {

            e.printStackTrace(System.out);
            System.out.println("ocurrio un error debido a " + e);
            getServletContext().setAttribute("Error", "ocurrio un error debido a " + e);
            //request.getRequestDispatcher("/gestion-administrativa.jsp").forward(request, response);
            req.getRequestDispatcher("gestion-bodega.jsp").forward(req, resp);
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

            req.getRequestDispatcher("gestion-bodega.jsp").forward(req, resp);

        } catch (Exception e) {

            e.printStackTrace(System.out);
            System.out.println("ocurrio un error debido a " + e);
            getServletContext().setAttribute("Error", "ocurrio un error debido a " + e);
            //request.getRequestDispatcher("/gestion-administrativa.jsp").forward(request, response);
            req.getRequestDispatcher("gestion-bodega.jsp").forward(req, resp);
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
