/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webServlets;

import dataBase.UsuarioDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import dominio.*;
import javax.servlet.RequestDispatcher;

/**
 *
 * @author sirbon
 */
@WebServlet("/loginServlet")
public class loginServlet extends HttpServlet {

    Usuario loggedUsuario;
    UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //leemos los parametros 

        String nick = request.getParameter("usuario");
        String pasword = request.getParameter("password");

        HttpSession session = request.getSession();
        boolean isLogged = isLogged(nick, pasword);
        
        if (isLogged && loggedUsuario.getLevelUsr() == 0) {

            response.sendRedirect("gestion-administrativa.jsp");
            session.setAttribute(nick, loggedUsuario.getPassword());

        } else if (isLogged && loggedUsuario.getLevelUsr() == 1) {
            response.sendRedirect("gestion-tienda.jsp");
            session.setAttribute(nick, loggedUsuario.getPassword());

        } else if (isLogged && loggedUsuario.getLevelUsr() == 2) {
            response.sendRedirect("gestion-bodega.jsp");
            session.setAttribute(nick, loggedUsuario.getPassword());

        } else if (isLogged && loggedUsuario.getLevelUsr() == 3) {
            response.sendRedirect("gestion-supervisora.jsp");
            session.setAttribute(nick, loggedUsuario.getPassword());

        } else {
            request.setAttribute("error", "Usuario o contraseña incorrectos");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }

    }

    public boolean isLogged(String username, String password) {
        Usuario tmpUsuario = usuarioDAO.logearUsuario(new Usuario(username, password));
        if (tmpUsuario.getLevelUsr() == -1) {
            return false;
        } else {

            loggedUsuario = tmpUsuario;

        }
        return true;
    }

}
