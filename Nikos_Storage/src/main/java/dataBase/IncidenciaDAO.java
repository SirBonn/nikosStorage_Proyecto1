/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataBase;

import dominio.Incidencia;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author sirbon
 */
public class IncidenciaDAO {

    public int insertarIncidencia(Incidencia incidencia) {

        final String SQL_INSERT = "INSERT INTO INCIDENCIAS  (codigo_incidencia, fecha_incidencia, estado_incidencia, "
                + "envio_incidente, bodeguero_encargado) VALUES (?, ?, ?, ?, ?)";

        int rowAffected = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DBConectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SQL_INSERT);

            preparedStatement.setInt(1, incidencia.getCodigo());
            preparedStatement.setTimestamp(2, incidencia.getTimestampDevolucion());
            preparedStatement.setString(3, incidencia.getEstadoIncidencia());
            preparedStatement.setInt(4, incidencia.getEnvioDevuelto());
            preparedStatement.setInt(5, incidencia.getEncargado());

            rowAffected = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("error al agregar una incidencai \n" + e);
        } finally {

            System.out.println("se agrego un incidencia correctamente");
            DBConectionManager.close(connection);
            DBConectionManager.close(preparedStatement);

        }

        return rowAffected;

    }
}
