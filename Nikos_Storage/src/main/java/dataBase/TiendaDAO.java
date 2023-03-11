/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataBase;

import dominio.Tienda;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sirbon
 */
public class TiendaDAO {

    public List<Tienda> listarTiendas() {

        final String SQL_SELECT = "SELECT codigo_tienda, nombre_tienda, direccion_tienda, tipo_tienda FROM TIENDAS";
        List<Tienda> tiendas = new ArrayList();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Tienda tienda = null;

        try {
            connection = DBConectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SQL_SELECT);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                int codigo = resultSet.getInt("codigo_tienda");
                String nombre = resultSet.getString("nombre");
                String direccion = resultSet.getString("direccion_tienda");
                String tipo_tienda = resultSet.getString("tipo_tienda");

                tienda = new Tienda(codigo, nombre, direccion, tipo_tienda);
                tiendas.add(tienda);

            }
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        } finally {
            DBConectionManager.close(connection);
            DBConectionManager.close(resultSet);
            DBConectionManager.close(preparedStatement);
        }

        return tiendas;
    }

    public Tienda buscarTienda(Tienda tienda) {
        final String SQL_SELECT_BY_ID = "SELECT codigo_tienda, nombre_tienda, direccion_tienda, tipo_tienda "
                + "FROM TIENDAS WHERE codigo_tienda = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SQL_SELECT_BY_ID);
            preparedStatement.setInt(1, tienda.getCodigo_tienda());

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String nombre = resultSet.getString("nombre_tienda");
                tienda.setNombre(nombre);
                String direccion = resultSet.getString("direccion_tienda");
                tienda.setDireccion(direccion);
                String tipo_tienda = resultSet.getString("tipo_tienda");
                tienda.setTipo(tipo_tienda);
            }

        } catch (SQLException e) {

            e.printStackTrace(System.out);

        } finally {

            DBConectionManager.close(connection);
            DBConectionManager.close(resultSet);
            DBConectionManager.close(preparedStatement);

        }

        return tienda;
    }

    public int insertarTienda(Tienda tienda) {

        final String SQL_INSERT = "INSERT INTO TIENDAS (codigo_tienda, nombre_tienda, direccion_tienda, tipo_tienda)"
                + " VALUES (?, ?, ?, ?)";

        int rowAffected = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DBConectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SQL_INSERT);

            preparedStatement.setInt(1, tienda.getCodigo_tienda());
            preparedStatement.setString(2, tienda.getNombre());
            preparedStatement.setString(3, tienda.getDireccion());
            preparedStatement.setString(4, tienda.getTipo());

            rowAffected = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("hubbo un error al agregar una tienda");
            e.printStackTrace(System.out);
        } finally {
            System.out.println("se agrego una tienda exitosamente");
            DBConectionManager.close(connection);
            DBConectionManager.close(preparedStatement);

        }

        return rowAffected;

    }

    public int actualizarTienda(Tienda tienda) {
        final String SQL_UPDATE = "UPDATE TIENDAS SET nombre_tienda=?, direccion_tienda=?, "
                + "tipo_tienda=? WHERE codigo_tienda=?";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int rowsAfected = 0;

        try {
            connection = DBConectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SQL_UPDATE);

            preparedStatement.setString(1, tienda.getNombre());
            preparedStatement.setString(2, tienda.getDireccion());
            preparedStatement.setString(3, tienda.getTipo());
            preparedStatement.setInt(4, tienda.getCodigo_tienda());

            rowsAfected = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace(System.out);
        } finally {

            DBConectionManager.close(connection);
            DBConectionManager.close(preparedStatement);

        }

        return rowsAfected;
    }

    public int eliminarTienda(Tienda tienda) {

        final String SQL_DELETE = "DELETE FROM TIENDAS WHERE codigo_tienda=?";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int rowsAfected = 0;

        try {
            connection = DBConectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SQL_DELETE);

            preparedStatement.setInt(1, tienda.getCodigo_tienda());

            rowsAfected = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace(System.out);
        } finally {

            DBConectionManager.close(connection);
            DBConectionManager.close(preparedStatement);

        }

        return rowsAfected;

    }

}
