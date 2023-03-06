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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //leemos los parametros 

        String nick = request.getParameter("usuario");
        String pasword = request.getParameter("password");

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario loggedUsuario = usuarioDAO.logearUsuario(new Usuario(nick, pasword));
        HttpSession session = request.getSession();
        session.setAttribute(nick, loggedUsuario.getPassword());

        if (loggedUsuario.getLevelUsr() == 0) {

            response.sendRedirect("gestion-administrativa.jsp");

        } else if (loggedUsuario.getLevelUsr() == 1) {
            response.sendRedirect("gestion-tienda.jsp");

        } else if (loggedUsuario.getLevelUsr() == 2) {
            response.sendRedirect("gestion-bodeguera.jsp");

        } else if (loggedUsuario.getLevelUsr() == 3) {
            response.sendRedirect("gestion-supervisora.jsp");

        } else if (loggedUsuario.getLevelUsr() < 0) {
            request.setAttribute("error", "Usuario o contraseña incorrectos");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
            dispatcher.forward(request, response);

        }

    }

}
