/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataBase;

import dominio.Devolucion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author sirbon
 */
public class DevolucionDAO {

    public int insertarDevolucion(Devolucion devolucion) {

        final String SQL_INSERT = "INSERT INTO DEVOLUCIONES  (codigo_devolucion, fecha_devolucion, estado_devolucion, "
                + "total_devuelto, envio_devuelto, bodeguero_encargado) VALUES (?, ?, ?, ?, ?, ?)";

        int rowAffected = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DBConectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SQL_INSERT);

            preparedStatement.setInt(1, devolucion.getCodigoDevolucion());
            preparedStatement.setTimestamp(2, devolucion.getTimestampDevolucion());
            preparedStatement.setString(3, devolucion.getEstadoDevolucion());
            preparedStatement.setDouble(4, devolucion.getTotalDevuelto());
            preparedStatement.setInt(5, devolucion.getEnvioDevuelto());
            preparedStatement.setInt(6, devolucion.getEncargado());

            rowAffected = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("error al agregar una devolucion \n" + e);
        } finally {

            System.out.println("se agrego un devolucion correctamente");
            DBConectionManager.close(connection);
            DBConectionManager.close(preparedStatement);

        }

        return rowAffected;

    }

}
