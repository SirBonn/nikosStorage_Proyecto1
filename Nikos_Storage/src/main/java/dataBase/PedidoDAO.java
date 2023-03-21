/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataBase;

import dominio.ListadoProductos;
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
            System.out.println("se agrego un pedido correctamente");

        } catch (SQLException e) {
            System.out.println("error al agregar un pedido\n" + e);
        } finally {

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
                Tienda tiendaDestino = new TiendaDAO().buscarTienda(new Tienda(tienda));
                pedido = new Pedido(codigo, fecha, costoTotal, estado, usuarioSolicitante, tiendaDestino);
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
                + "estado_pedido, usuario_solicitante, tienda_solicitante FROM PEDIDOS WHERE codigo_pedido=?";

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
                pedido.setDependiente(usuarioSolicitante);
                ListadoProductos listadoProductos = new ListadoProductosDAO().listarPedido(pedido);
                pedido.setListadoProductos(listadoProductos);
                Tienda tienda =new TiendaDAO().buscarTienda(new Tienda(resultSet.getInt("tienda_solicitante")));
                pedido.setTienda(tienda);
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

    public int actualizarPedidos(Pedido pedido) {

        final String SQL_UPDATE = "UPDATE PEDIDOS SET estado_pedido = ? "
                + "WHERE codigo_pedido=?";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int rowsAffected = 0;

        try {
            connection = DBConectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SQL_UPDATE);

            preparedStatement.setString(1, pedido.getEstadoPedido());
            preparedStatement.setInt(2, pedido.getCodigoPedido());

            rowsAffected = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace(System.out);
        } finally {

            DBConectionManager.close(connection);
            DBConectionManager.close(preparedStatement);

        }

        return rowsAffected;
    }

    public List<Usuario> getTopUsrPedidos() {

        final String SQL_SELECT = "SELECT DEPENDIENTES.codigo, COUNT(*) as totalPedidos FROM DEPENDIENTES "
                + "JOIN PEDIDOS ON DEPENDIENTES.codigo = PEDIDOS.usuario_solicitante GROUP BY DEPENDIENTES.CODIGO "
                + "ORDER BY totalPedidos DESC";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Usuario usuario = null;
        List<Usuario> usuarios = new ArrayList<>();

        try {
            connection = DBConectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SQL_SELECT);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int codigo = resultSet.getInt("DEPENDIENTES.codigo");
                usuario = new UsuarioDAO().buscarUsuario(new Usuario(codigo), "DEPENDIENTES");
                int cantidad = resultSet.getInt("totalPedidos");
                usuario.setCantidad(cantidad);
                usuarios.add(usuario);
            }

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
            this.informe = ex.getMessage();

        } finally {

            DBConectionManager.close(connection);
            DBConectionManager.close(resultSet);
            DBConectionManager.close(preparedStatement);

        }

        return usuarios;
    }
    
    public List<Tienda> getTopTiendasEnvios() {

        final String SQL_SELECT = "SELECT TIENDAS.codigo_tienda, COUNT(*) AS totalPedidos FROM TIENDAS JOIN "
                + "PEDIDOS ON TIENDAS.codigo_tienda = PEDIDOS.tienda_solicitante GROUP BY TIENDAS.codigo_tienda "
                + "ORDER BY totalPedidos DESC;";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Tienda tienda = null;
        List<Tienda> tiendas = new ArrayList<>();

        try {
            connection = DBConectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SQL_SELECT);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int codigo = resultSet.getInt("TIENDAS.codigo_tienda");
                tienda = new TiendaDAO().buscarTienda(new Tienda(codigo));
                int cantidad = resultSet.getInt("totalPedidos");
                tienda.setCantidad(cantidad);
                tiendas.add(tienda);
            }

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
            this.informe = ex.getMessage();

        } finally {

            DBConectionManager.close(connection);
            DBConectionManager.close(resultSet);
            DBConectionManager.close(preparedStatement);

        }

        return tiendas;
    }
    
    
    
}
