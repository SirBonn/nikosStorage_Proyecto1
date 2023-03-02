/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webServlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import Security.PasswordEncoder;

/**
 *
 * @author sirbon
 */
@WebServlet("/loginServlet")
public class loginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //leemos los parametros 
        
        String usuario = request.getParameter("usuario");
        String pasword = request.getParameter("password");
        String encodePswrd = PasswordEncoder.encodePassword(pasword);
        System.out.println("usuario" + usuario);
        System.out.println("password" + encodePswrd);
        
        PrintWriter out = response.getWriter();
        
        out.print("<html>");
        out.print("<body>");
        out.print("el parametro usuario es " + usuario);
        out.print("</br>");
        out.print("el parametro contraseña es " +encodePswrd);
        out.print("</br>");
        out.print("</body>");
        out.print("</html>");
        out.close();
        
    }

}
