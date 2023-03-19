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
@WebServlet("/ManageControlServlet")
@javax.servlet.annotation.MultipartConfig
public class ManageControlServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String accion = req.getParameter("accion");

        if (accion != null) {

            switch (accion) {
                case "ediarUsuario":
                    this.editarUsuario(req, resp);
                    break;
                case "eliminarUsuario":
                    this.eliminarUsuario(req, resp);
                    break;
                case "eliminarTienda":
                    this.eliminarTienda(req, resp);
                    break;
                case "cerrarSesion":
                    this.cerrarSesion(req, resp);
                    break;
            }

        } else {
            this.listarElementos(req, resp);

        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String accion = req.getParameter("accion");

        if (accion != null) {

            switch (accion) {
                case "cargarArchivos":
                    this.agregarArchivos(req, resp);
                    break;
                case "agregarUsuario":
                    this.agregarUsuario(req, resp);
                    break;
                case "actualizarUsuario":
                    this.actualizarUsuario(req, resp);
                    break;
                case "agregarTienda":
                    this.agregarTienda(req, resp);

            }

        }

    }

    private void listarElementos(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Usuario> dependientes = new UsuarioDAO().listarUsuarios("DEPENDIENTES");
        List<Usuario> bodegueros = new UsuarioDAO().listarUsuarios("BODEGUEROS");
        List<Usuario> supervisores = new UsuarioDAO().listarUsuarios("SUPERVISORES");
        List<Tienda> tiendas = new TiendaDAO().listarTiendas();
        req.setAttribute("dependientes", dependientes);
        req.setAttribute("bodegueros", bodegueros);
        req.setAttribute("supervisores", supervisores);
        req.setAttribute("tiendas", tiendas);

        req.getRequestDispatcher("/WEB-INF/pages/magnamentPages/administrative.jsp").forward(req, resp);
    }

    private void agregarArchivos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int rows = 0;
        boolean isJson = false;
        try {
            System.out.println("ingresamos al servlet");
            Part filePart = request.getPart("JSONfile"); // obtiene la parte del archivo cargado en el input
            String fileName = filePart.getSubmittedFileName();
            isJson = fileName.toLowerCase().endsWith(".json");
            if (isJson) {
                InputStream fileContent = filePart.getInputStream(); // obtiene el InputStream del archivo
                BufferedReader reader = new BufferedReader(new InputStreamReader(fileContent, "UTF-8")); // crea un lector de BufferedReader
                StringBuilder stringBuilder = new StringBuilder(); // crea un StringBuilder para almacenar el contenido del archivo
                String line;

                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line); // agrega cada línea del archivo al StringBuilder
                }

                FileManager fileManager = new FileManager(stringBuilder.toString(), reader);
                rows = fileManager.cargarDatos();
            }

        } catch (IOException | IllegalArgumentException | ServletException e) {
            System.out.println("error por " + e);
            e.printStackTrace(System.out);
        }

        if (rows != 0) {
            getServletContext().setAttribute("Exito", "Se cargo con exito el archivo .JSON");
            request.getRequestDispatcher("gestion-administrativa.jsp").forward(request, response);
            //response.sendRedirect("/gestion-administrativa.jsp");

        } else {
            getServletContext().setAttribute("Error", "Verifica el archivo y vuelve a intentar");
            request.getRequestDispatcher("gestion-administrativa.jsp").forward(request, response);

        }
    }

    private void agregarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {

            UsuarioDAO usuarioDAO = new UsuarioDAO();
            String nombre = request.getParameter("nombre");
            String apellido = request.getParameter("apellido");
            String username = request.getParameter("username");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String tienda = request.getParameter("tienda");
            String tipo = request.getParameter("tipoUsuario");
            Usuario usuario = new Usuario(nombre, apellido, username, password, email, Integer.parseInt(tienda));
            int rows = usuarioDAO.insertUsuario(usuario, tipo);

            if (rows != 0) {
                getServletContext().setAttribute("Exito", "Se agrego el usuario " + nombre);
            } else {
                getServletContext().setAttribute("Error", "ocurrio un error " + usuarioDAO.getInforme());
            }
            request.getRequestDispatcher("gestion-administrativa.jsp").forward(request, response);

        } catch (IOException | NumberFormatException | ServletException e) {
            System.out.println("ocurrio un error debido a " + e);

            getServletContext().setAttribute("Error", "ocurrio un error debido a " + e);
            //request.getRequestDispatcher("/gestion-administrativa.jsp").forward(request, response);
            request.getRequestDispatcher("gestion-administrativa.jsp").forward(request, response);
        }

    }

    private void editarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int codigo = Integer.parseInt(request.getParameter("codigoUsuario"));
        String table = request.getParameter("table");

        Usuario usuario = new UsuarioDAO().buscarUsuario(new Usuario(codigo), table);
        request.setAttribute("usrEditable", usuario);
        request.setAttribute("usrTable", table);

        String jspEdit = "/WEB-INF/pages/magnamentPages/editar-usuario.jsp";
        request.getRequestDispatcher(jspEdit).forward(request, response);
    }

    private void actualizarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int rows = 0;

        try {

            String nombre = request.getParameter("nombre");
            String apellido = request.getParameter("apellido");
            String username = request.getParameter("username");
            String email = request.getParameter("email");
            int tienda = Integer.parseInt(request.getParameter("tienda"));
            int codigoUsr = Integer.parseInt(request.getParameter("codigoUsuario"));
            String tableUsr = request.getParameter("tableUsr");
            String tipo = request.getParameter("tipoUsuario");

            UsuarioDAO usuarioDAO = new UsuarioDAO();
            Usuario usuario = new Usuario(codigoUsr);
            usuario.setApellido(apellido);
            usuario.setNombre(nombre);
            usuario.setNickName(username);
            usuario.setEmail(email);
            usuario.setTienda(tienda);

            if (tipo.equals(tableUsr)) {
                usuarioDAO.actualizarUsuario(usuario, tableUsr);
            } else {
                usuarioDAO.eliminarUsuario(usuario, tableUsr);
                usuarioDAO.insertUsuario(usuario, tipo);
            }

            if (rows != 0) {
                getServletContext().setAttribute("Exito", "Se actualizo el usuario " + nombre);
            } else {
                getServletContext().setAttribute("Error", "ocurrio un error debido a " + usuarioDAO.getInforme());
            }

            request.getRequestDispatcher("gestion-administrativa.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            System.out.println("ocurrio un error debido a " + e);
            getServletContext().setAttribute("Error", "ocurrio un error debido a " + e);
            //request.getRequestDispatcher("/gestion-administrativa.jsp").forward(request, response);
            request.getRequestDispatcher("gestion-administrativa.jsp").forward(request, response);
        }

    }

    private void eliminarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {

            int codigoUsr = Integer.parseInt(request.getParameter("codigoUsuario"));
            String tableUsr = request.getParameter("tableUsr");

            UsuarioDAO usuarioDAO = new UsuarioDAO();

            Usuario usuario = new Usuario(codigoUsr);

            int rows = usuarioDAO.eliminarUsuario(usuario, tableUsr);

            if (rows != 0) {
                getServletContext().setAttribute("Exito", "Se elimino el usuario ");
            } else {
                getServletContext().setAttribute("Error", "ocurrio un error debido a " + usuarioDAO.getInforme());
            }
            request.getRequestDispatcher("gestion-administrativa.jsp").forward(request, response);

        } catch (IOException | NumberFormatException | ServletException e) {

            System.out.println(e);
            getServletContext().setAttribute("Error", "ocurrio un error debido a " + e);
            //request.getRequestDispatcher("/gestion-administrativa.jsp").forward(request, response);
            request.getRequestDispatcher("gestion-administrativa.jsp").forward(request, response);

        }
    }

    private void agregarTienda(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {

            TiendaDAO tiendaDao = new TiendaDAO();
            String nombre = request.getParameter("nombre");
            String direccion = request.getParameter("direccion");
            String esSupervisada = request.getParameter("tipoTienda");
            if (esSupervisada == null) {
                esSupervisada = "normal";
            }

            Tienda tienda = new Tienda(nombre, direccion, esSupervisada);

            int rows = tiendaDao.insertarTienda(tienda);

            if (rows != 0) {
                getServletContext().setAttribute("Exito", "Se agrego la tienda " + nombre);
            } else {
                getServletContext().setAttribute("Error", "ocurrio un error " + tiendaDao.getInforme());
            }
            request.getRequestDispatcher("gestion-administrativa.jsp").forward(request, response);

        } catch (IOException | ServletException e) {

            getServletContext().setAttribute("Error", "ocurrio un error " + e);
            e.printStackTrace(System.out);
            request.getRequestDispatcher("gestion-administrativa.jsp").forward(request, response);

        }

    }

    private void eliminarTienda(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {

            int codigoTdn = Integer.parseInt(request.getParameter("codigoTienda"));

            TiendaDAO tiendaDAO = new TiendaDAO();

            Tienda tienda = new Tienda(codigoTdn);

            int rows = tiendaDAO.eliminarTienda(tienda);

            if (rows != 0) {
                getServletContext().setAttribute("Exito", "Se elimino la Tienda ");
            } else {
                getServletContext().setAttribute("Error", "ocurrio un error debido a " + tiendaDAO.getInforme());
            }
            request.getRequestDispatcher("gestion-administrativa.jsp").forward(request, response);

        } catch (IOException | NumberFormatException | ServletException e) {

            System.out.println(e);
            getServletContext().setAttribute("Error", "ocurrio un error debido a " + e);
            //request.getRequestDispatcher("/gestion-administrativa.jsp").forward(request, response);
            request.getRequestDispatcher("gestion-administrativa.jsp").forward(request, response);

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
