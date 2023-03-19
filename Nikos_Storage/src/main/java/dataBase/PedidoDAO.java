/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataBase;

import dominio.Pedido;
import dominio.Tienda;
import dominio.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

/**
 *
 * @author sirbon
 */
@Getter
public class PedidoDAO {

    private String informe = "";

    public int insertarPedido(Pedido pedido) {

        final String SQL_INSERT = "INSERT INTO PEDIDOS  (codigo_pedido, fecha_pedido, costo_total_pedido,"
                + "estado_pedido, usuario_solicitante, tienda_solicitante) VALUES (?, ?, ?, ?, ?, ?)";

        int rowAffected = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DBConectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SQL_INSERT);

            preparedStatement.setInt(1, pedido.getCodigoPedido());
            preparedStatement.setString(2, pedido.getDatePedido());
            preparedStatement.setDouble(3, pedido.getCostoPedido());
            preparedStatement.setString(4, pedido.getEstadoPedido());
            preparedStatement.setInt(5, pedido.getDependienteSolicitante());
            preparedStatement.setInt(6, pedido.getTienda().getCodigo_tienda());

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

    public List<Pedido> listarPedidos() {

        final String SQL_SELECT = "SELECT codigo_pedido, fecha_pedido, costo_total_pedido,"
                + "estado_pedido, usuario_solicitante, tienda_solicitante FROM PEDIDOS";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Pedido pedido = null;
        List<Pedido> pedidos = new ArrayList<>();

        try {
            connection = DBConectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SQL_SELECT);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int codigo = resultSet.getInt("codigo_pedido");
                String fecha = resultSet.getString("fecha_pedido");
                double costoTotal = resultSet.getDouble("costo_total_pedido");
                String estado = resultSet.getString("estado_pedido");
                Usuario usuarioSolicitante = new UsuarioDAO().buscarUsuario(new Usuario(resultSet.getInt("usuario_solicitante")), "DEPENDIENTES");
                Tienda tienda = new TiendaDAO().buscarTienda(new Tienda(resultSet.getInt("tienda_solicitante")));
                pedido = new Pedido(codigo, fecha, costoTotal, estado, usuarioSolicitante, tienda);

                pedidos.add(pedido);

            }

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
            this.informe += ex.getMessage() + "\n";

        } finally {

            DBConectionManager.close(connection);
            DBConectionManager.close(resultSet);
            DBConectionManager.close(preparedStatement);

        }

        return pedidos;
    }

    public List<Pedido> listarPedidos(int tienda) {

        final String SQL_SELECT = "SELECT codigo_pedido, fecha_pedido, costo_total_pedido,"
                + "estado_pedido, usuario_solicitante, tienda_solicitante FROM PEDIDOS WHERE tienda_solicitante=? ";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Pedido pedido = null;
        List<Pedido> pedidos = new ArrayList<>();

        try {
            connection = DBConectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SQL_SELECT);
            preparedStatement.setInt(1, tienda);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int codigo = resultSet.getInt("codigo_pedido");
                String fecha = resultSet.getString("fecha_pedido");
                double costoTotal = resultSet.getDouble("costo_total_pedido");
                String estado = resultSet.getString("estado_pedido");
                Usuario usuarioSolicitante = new UsuarioDAO().buscarUsuario(new Usuario(resultSet.getInt("usuario_solicitante")), "DEPENDIENTES");
                pedido = new Pedido(codigo, fecha, costoTotal, estado, usuarioSolicitante);

                pedidos.add(pedido);

            }

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
            this.informe += ex.getMessage() + "\n";

        } finally {

            DBConectionManager.close(connection);
            DBConectionManager.close(resultSet);
            DBConectionManager.close(preparedStatement);

        }

        return pedidos;
    }

    public Pedido buscarPedido(Pedido pedido) {

        final String SQL_SELECT_BY_ID = "SELECT fecha_pedido, costo_total_pedido,"
                + "estado_pedido, usuario_solicitante FROM PEDIDOS WHERE codigo_pedido=?";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SQL_SELECT_BY_ID);
            preparedStatement.setInt(1, pedido.getCodigoPedido());
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String fecha = resultSet.getString("fecha_pedido");
                pedido.setFechaPedido(pedido.setLocalDate(fecha));
                double costoTotal = resultSet.getDouble("costo_total_pedido");
                pedido.setCostoPedido(costoTotal);
                String estado = resultSet.getString("estado_pedido");
                pedido.setEstadoPedido(estado);
                Usuario usuarioSolicitante = new UsuarioDAO().buscarUsuario(new Usuario(resultSet.getInt("usuario_solicitante")), "DEPENDIENTES");
                pedido.setDependienteSolicitante(usuarioSolicitante);
            }

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
            this.informe += ex.getMessage() + "\n";

        } finally {

            DBConectionManager.close(connection);
            DBConectionManager.close(resultSet);
            DBConectionManager.close(preparedStatement);

        }

        return pedido;
    }

}
