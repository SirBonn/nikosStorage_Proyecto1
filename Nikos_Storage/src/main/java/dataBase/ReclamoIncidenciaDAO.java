/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataBase;

import dominio.Producto;
import dominio.ReclamoIncidencia;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author sirbon
 */
public class ReclamoIncidenciaDAO {

    public int incertarReclamo(ReclamoIncidencia reclamoIncidencia) {

        final String SQL_INSERT = "INSERT INTO RECLAMOS_INCIDENCIAS  (codigo_incidencia, cantidad_afectada, "
                + "motivo_incidencia, codigo_producto_incidente) VALUES (?, ?, ?, ?)";

        int rowAffected = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DBConectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SQL_INSERT);
            ArrayList<Producto> productos = reclamoIncidencia.getProductos();

            for (Producto producto : productos) {

                preparedStatement.setInt(1, reclamoIncidencia.getCodigoIncidencia());
                preparedStatement.setInt(2, producto.getCantidad());
                preparedStatement.setString(3, reclamoIncidencia.getMotivoIncidencia());
                preparedStatement.setInt(4, producto.getCodigo());

                rowAffected = preparedStatement.executeUpdate();
            }

        } catch (SQLException e) {
            System.out.println("error al agregar un reclamo\n" + e);
        } finally {

            System.out.println("se agrego un reclamo correctamente");
            DBConectionManager.close(connection);
            DBConectionManager.close(preparedStatement);

        }

        return rowAffected;

    }
}
