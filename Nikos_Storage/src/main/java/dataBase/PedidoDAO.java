/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataBase;

import dominio.Pedido;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author sirbon
 */
public class PedidoDAO {

    public int insertarPedido(Pedido pedido) {

        final String SQL_INSERT = "INSERT INTO PEDIDOS  (codigo_pedido, fecha_pedido, costo_total_pedido,"
                + "estado_pedido, usuario_solicitante) VALUES (?, ?, ?, ?, ?)";

        int rowAffected = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DBConectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SQL_INSERT);

            preparedStatement.setInt(1, pedido.getCodigoPedido());
            preparedStatement.setTimestamp(2, pedido.getTimestampPedido());
            preparedStatement.setDouble(3, pedido.getCostoPedido());
            preparedStatement.setString(4, pedido.getEstadoPedido());
            preparedStatement.setInt(5, pedido.getDependienteSolicitante());

            rowAffected = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("error al agregar un pedido\n" + e);
        } finally {

            System.out.println("se agrego un pedido correctamente");
            DBConectionManager.close(connection);
            DBConectionManager.close(preparedStatement);

        }

        return rowAffected;

    }
}
