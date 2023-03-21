/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataBase;

import dominio.Envio;
import dominio.Pedido;
import dominio.Tienda;
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
public class EnvioDAO {

    private String informe = "";

    public int insertarEnvio(Envio envio) {

        final String SQL_INSERT = "INSERT INTO ENVIOS  (fecha_envio, fecha_recepcion, "
                + "estado_envio, precio_envio, productos_enviados, tienda_destino) VALUES (?, ?, ?, ?, ?, ?)";

        int rowAffected = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DBConectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SQL_INSERT);

            preparedStatement.setString(1, envio.getDateEnvio());
            preparedStatement.setString(2, envio.getDateRecepcion());
            preparedStatement.setString(3, envio.getEstado());
            preparedStatement.setDouble(4, envio.getTotalEnvio());
            preparedStatement.setInt(5, envio.getPedidoEnviado().getCodigoPedido()); //referencia al pedido de envio
            preparedStatement.setInt(6, envio.getTiendaDestino());

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

    public List<Envio> listarEnvios() {

        final String SQL_SELECT = "SELECT codigo_envio, fecha_envio, fecha_recepcion, "
                + "estado_envio, precio_envio, productos_enviados, tienda_destino FROM ENVIOS";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Envio envio = null;
        List<Envio> envios = new ArrayList<>();

        try {
            connection = DBConectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SQL_SELECT);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int codigo = resultSet.getInt("codigo_envio");
                String fechaEnv = resultSet.getString("fecha_envio");
                String fechaRec = resultSet.getString("fecha_recepcion");
                String estado = resultSet.getString("estado_envio");
                double precioEnv = resultSet.getDouble("precio_envio");
                int codigoPedido = resultSet.getInt("productos_enviados");
                Pedido pedidoEnv = new PedidoDAO().buscarPedido(new Pedido(codigoPedido));
                Tienda tienda = new Tienda(resultSet.getInt("tienda_destino"));

                envio = new Envio(codigo, fechaEnv, fechaRec, estado, precioEnv, pedidoEnv, tienda);
                envios.add(envio);

            }

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
            this.informe += ex.getMessage() + "\n";

        } finally {

            DBConectionManager.close(connection);
            DBConectionManager.close(resultSet);
            DBConectionManager.close(preparedStatement);

        }

        return envios;
    }

    public List<Envio> listarEnvios(int tiendaKey) {

        final String SQL_SELECT = "SELECT codigo_envio, fecha_envio, fecha_recepcion, "
                + "estado_envio, precio_envio, productos_enviados FROM ENVIOS WHERE tienda_destino = ?";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Envio envio = null;
        List<Envio> envios = new ArrayList<>();

        try {
            connection = DBConectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SQL_SELECT);
            preparedStatement.setInt(1, tiendaKey);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int codigo = resultSet.getInt("codigo_envio");
                String fechaEnv = resultSet.getString("fecha_envio");
                String fechaRec = resultSet.getString("fecha_recepcion");
                String estado = resultSet.getString("estado_envio");
                double precioEnv = resultSet.getDouble("precio_envio");
                int codigoPedido = resultSet.getInt("productos_enviados");
                Pedido pedidoEnv = new PedidoDAO().buscarPedido(new Pedido(codigoPedido));
                Tienda tienda = new Tienda(tiendaKey);

                envio = new Envio(codigo, fechaEnv, fechaRec, estado, precioEnv, pedidoEnv, tienda);
                envios.add(envio);

            }

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
            this.informe += ex.getMessage() + "\n";

        } finally {

            DBConectionManager.close(connection);
            DBConectionManager.close(resultSet);
            DBConectionManager.close(preparedStatement);

        }

        return envios;
    }

    public Envio buscarEnvio(Envio envio) {

        final String SQL_SELECT_BY_ID = "SELECT fecha_envio, fecha_recepcion, "
                + "estado_envio, precio_envio, productos_enviados, tienda_destino FROM ENVIOS WHERE codigo_envio=?";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SQL_SELECT_BY_ID);
            preparedStatement.setInt(1, envio.getCodigoEnvio());
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String fechaRec = resultSet.getString("fecha_recepcion");
                envio.setFechaRecepcion(envio.setLocalDate(fechaRec));
                String fechaEnv = resultSet.getString("fecha_envio");
                envio.setFechaEnvio(envio.setLocalDate(fechaEnv));

                double costoTotal = resultSet.getDouble("precio_envio");
                envio.setTotalEnvio(costoTotal);
                String estado = resultSet.getString("estado_envio");
                envio.setEstado(estado);
                int codigoPedido = resultSet.getInt("productos_enviados");
                Pedido pedido = new PedidoDAO().buscarPedido(new Pedido(codigoPedido));
                
                envio.setPedidoEnviado(pedido);
                Tienda tienda = new TiendaDAO().buscarTienda(new Tienda(resultSet.getInt("tienda_destino")));
                envio.setTiendaDestino(tienda);

//                Usuario usuarioSolicitante = new UsuarioDAO().buscarUsuario(new Usuario(resultSet.getInt("usuario_solicitante")), "DEPENDIENTES");
//                envio.setDependienteSolicitante(usuarioSolicitante);
            }

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
            this.informe += ex.getMessage() + "\n";

        } finally {

            DBConectionManager.close(connection);
            DBConectionManager.close(resultSet);
            DBConectionManager.close(preparedStatement);

        }

        return envio;
    }

    public int actualizarEnvio(Envio envio) {

        final String SQL_UPDATE = "UPDATE ENVIOS SET estado_envio = ?, fecha_recepcion = ? "
                + "WHERE codigo_envio=?";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int rowsAffected = 0;

        try {
            connection = DBConectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SQL_UPDATE);

            preparedStatement.setString(1, envio.getEstado());
            preparedStatement.setString(2, envio.getDateRecepcion());
            preparedStatement.setInt(3, envio.getCodigoEnvio());

            rowsAffected = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace(System.out);
        } finally {

            DBConectionManager.close(connection);
            DBConectionManager.close(preparedStatement);

        }

        return rowsAffected;
    }

}
