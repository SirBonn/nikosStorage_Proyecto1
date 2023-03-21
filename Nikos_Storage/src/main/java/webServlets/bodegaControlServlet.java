
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

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String accion = req.getParameter("accion");

        if (accion != null) {

        } else {
            this.listarElementos(req, resp);
        }

    }

    private void listarElementos(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        Usuario usuario = (Usuario) session.getAttribute("bodeguero");

        List<Incidencia> incidencias = new IncidenciaDAO().listarIncidencias();
        req.setAttribute("incidencias", incidencias);
        List<Envio> envios = new EnvioDAO().listarEnvios();
        req.setAttribute("envios", envios);
        List<Pedido> pedidos = new PedidoDAO().listarPedidos();
        req.setAttribute("pedidos", pedidos);
        List<Devolucion> devoluciones = new DevolucionDAO().listarDevoluciones();
        req.setAttribute("devoluciones", devoluciones);
        req.getRequestDispatcher("/WEB-INF/pages/bodegaPages/bodega.jsp").forward(req, resp);

    }

}
