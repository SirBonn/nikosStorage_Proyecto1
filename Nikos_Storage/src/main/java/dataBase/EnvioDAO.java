/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataBase;

import dominio.Envio;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author sirbon
 */
public class EnvioDAO {
    
    public int insertarEnvio(Envio envio) {

        final String SQL_INSERT = "INSERT INTO ENVIOS  (codigo_envio, fecha_envio, fecha_recepcion, "
                + "estado_envio, precio_envio, productos_enviados, tienda_destino) VALUES (?, ?, ?, ?, ?, ?, ?)";

        int rowAffected = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DBConectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SQL_INSERT);

            preparedStatement.setInt(1, envio.getCodigoEnvio());
            preparedStatement.setTimestamp(2, envio.getTimestampEnvio());
            preparedStatement.setTimestamp(3, envio.getTimestampRecepcion());
            preparedStatement.setString(4, envio.getEstado());
            preparedStatement.setDouble(5, envio.getTotalEnvio());
            preparedStatement.setInt(6, envio.getProductosEnviados());
            preparedStatement.setInt(7, envio.getTiendaDestino());
            

            rowAffected = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("error al agregar un Envio\n" + e);
        } finally {

            System.out.println("se agrego un envio correctamente");
            DBConectionManager.close(connection);
            DBConectionManager.close(preparedStatement);

        }

        return rowAffected;

    }
}
