/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataBase;

import dominio.Producto;
import dominio.Tienda;
import dominio.Catalogo;
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
public class CatalogoDAO {

    private ProductoDAO productoDAO = new ProductoDAO();

    public List<Producto> listarCatalogo(Tienda tienda) {

        final String SQL_SELECT = "SELECT codigo_producto, existencia_producto FROM CATALOGO WHERE"
                + " codigo_tienda =?";
        List<Producto> productos = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SQL_SELECT);
            preparedStatement.setInt(1, tienda.getCodigo_tienda());
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                Producto producto = new Producto(resultSet.getInt("codigo_producto"));
                producto = productoDAO.buscarProducto(producto);
                producto.setCantidad(resultSet.getInt("existencia_producto"));

                productos.add(producto);

            }

        } catch (SQLException e) {
            e.printStackTrace(System.out);
        } finally {
            DBConectionManager.close(connection);
            DBConectionManager.close(resultSet);
            DBConectionManager.close(preparedStatement);
        }

        return productos;
    }

    public int insertarCatalogo(Catalogo catalogo) {

        final String SQL_INSERT = "INSERT INTO CATALOGO (codigo_tienda, codigo_producto, existencia_producto) "
                + "VALUES (?, ?, ?)";

        int rowAffected = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DBConectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SQL_INSERT);
            List<Producto> productos = catalogo.getProductos();

            for (Producto producto : productos) {

                preparedStatement.setInt(1, catalogo.getCodigoTienda());
                preparedStatement.setInt(2, producto.getCodigo());
                preparedStatement.setInt(3, producto.getCantidad());
                rowAffected = preparedStatement.executeUpdate();
                System.out.println("se agrego un catalogo correctamente");

            }

        } catch (SQLException e) {
            System.out.println("error al agregar un catalogo\n" + e);
        } finally {

            DBConectionManager.close(connection);
            DBConectionManager.close(preparedStatement);

        }

        return rowAffected;

    }

    public int actualizarProducto(int tienda, List<Producto> productos) {
        final String SQL_UPDATE = "UPDATE CATALOGO SET "
                + "existencia_producto = ? WHERE codigo_tienda=? && codigo_producto=?";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int rowsAfected = 0;

        try {

            for (Producto producto : productos) {
                connection = DBConectionManager.getConnection();
                preparedStatement = connection.prepareStatement(SQL_UPDATE);
                int nuevaCantidad = producto.getCantidad();
                Producto productoCat = buscarProducto(producto, tienda);
                producto.setCantidad(productoCat.getCantidad() - nuevaCantidad);

                preparedStatement.setInt(1, producto.getCantidad());
                preparedStatement.setInt(2, tienda);
                preparedStatement.setInt(3, producto.getCodigo());

                rowsAfected = preparedStatement.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace(System.out);
        } finally {

            DBConectionManager.close(connection);
            DBConectionManager.close(preparedStatement);

        }

        return rowsAfected;
    }

    public Producto buscarProducto(Producto producto, int tienda) {
        final String SQL_SELECT_BY_ID = "SELECT existencia_producto FROM CATALOGO WHERE codigo_tienda=? && codigo_producto=?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SQL_SELECT_BY_ID);
            preparedStatement.setInt(1, tienda);
            preparedStatement.setInt(2, producto.getCodigo());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int existencia = resultSet.getInt("existencia_producto");
                producto.setCantidad(existencia);
            }

        } catch (SQLException e) {
            e.printStackTrace(System.out);

        } finally {

            DBConectionManager.close(connection);
            DBConectionManager.close(resultSet);
            DBConectionManager.close(preparedStatement);

        }

        return producto;
    }

}
