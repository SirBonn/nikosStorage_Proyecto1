/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webServlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import fileMagnament.FileManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author sirbon
 */
@WebServlet("/filesMagnamentServlet")
@javax.servlet.annotation.MultipartConfig
public class filesMagnamentServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            System.out.println("ingresamos al servlet");
            Part filePart = request.getPart("JSONfile"); // obtiene la parte del archivo cargado en el input
            InputStream fileContent = filePart.getInputStream(); // obtiene el InputStream del archivo
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileContent, "UTF-8")); // crea un lector de BufferedReader
            StringBuilder stringBuilder = new StringBuilder(); // crea un StringBuilder para almacenar el contenido del archivo
            String line;
            
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line); // agrega cada línea del archivo al StringBuilder
            }
            
            FileManager fileManager = new FileManager(stringBuilder.toString(), reader);
            fileManager.cargarDatos();
            
        } catch (IOException | IllegalArgumentException | ServletException e) {
            System.out.println("se crasheo" + e);
            e.printStackTrace();
        }

    }

}
