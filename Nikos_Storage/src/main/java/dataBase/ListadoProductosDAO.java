/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataBase;

import dominio.*;
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
public class ListadoProductosDAO {

    public int insertarProducto(ListadoProductos lIstadoProductos) {

        final String SQL_INSERT = "INSERT INTO LISTADO_PRODUCTOS  (codigo_pedido, codigo_producto, cantidad, "
                + "precio_costo, precio_total) VALUES (?, ?, ?, ?, ?)";

        CatalogoDAO catalogoDAO = new CatalogoDAO();
        int rowAffected = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DBConectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SQL_INSERT);
            List<Producto> productos = lIstadoProductos.getListadoProductos();

            for (Producto producto : productos) {

                preparedStatement.setInt(1, lIstadoProductos.getCodigoListado());
                preparedStatement.setInt(2, producto.getCodigo());
                preparedStatement.setInt(3, producto.getCantidad());
                preparedStatement.setDouble(4, producto.getPrecioVenta());
                preparedStatement.setDouble(5, producto.getTotal());
                rowAffected = preparedStatement.executeUpdate();
                System.out.println("se agrego un producto correctamente");

            }

        } catch (SQLException e) {
            System.out.println("error al agregar un producto\n" + e);
        } finally {

            DBConectionManager.close(connection);
            DBConectionManager.close(preparedStatement);

        }

        return rowAffected;

    }

    public List<Producto> listarProductos(Pedido pedido) {

        final String SQL_SELECT = "SELECT codigo_producto, cantidad, "
                + "precio_costo, precio_total FROM LISTADO_PRODUCTOS WHERE codigo_pedido=?";

        List<Producto> productos = new ArrayList<>();
        Producto producto;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SQL_SELECT);
            preparedStatement.setInt(1, pedido.getCodigoPedido());

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int codigoProducto = resultSet.getInt("codigo_producto");
                int cantidadProducto = resultSet.getInt("cantidad");
                double costo = resultSet.getDouble("precio_costo"); //precio del producto
                double precio = resultSet.getDouble("precio_total"); //precio del producto * cantidad de productos

                producto = new Producto(codigoProducto, costo, cantidadProducto);
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

    public Producto obtenerProducto(Pedido pedido, Producto producto) {

        final String SQL_SELECT = "SELECT cantidad, "
                + "precio_costo, precio_total FROM LISTADO_PRODUCTOS WHERE codigo_pedido=? && codigo_producto=?";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SQL_SELECT);
            preparedStatement.setInt(1, pedido.getCodigoPedido());
            preparedStatement.setInt(2, producto.getCodigo());

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int cantidadProducto = resultSet.getInt("cantidad");
                producto.setCantidad(cantidadProducto);
                double costo = resultSet.getDouble("precio_costo");
                producto.setPrecioVenta(costo);
                double precio = resultSet.getDouble("precio_total"); //precio del producto * cantidad de productos

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

    public ListadoProductos listarPedido(Pedido pedido) {

        final String SQL_SELECT = "SELECT codigo_producto, cantidad, "
                + "precio_costo, precio_total FROM LISTADO_PRODUCTOS WHERE codigo_pedido=?";

        ListadoProductos listadoProductos = new ListadoProductos(pedido.getCodigoPedido());
        List<Producto> productos = new ArrayList<>();
        Producto producto;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SQL_SELECT);
            preparedStatement.setInt(1, pedido.getCodigoPedido());

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int codigoProducto = resultSet.getInt("codigo_producto");
                int cantidadProducto = resultSet.getInt("cantidad");
                double costo = resultSet.getDouble("precio_costo"); //precio del producto
                double precio = resultSet.getDouble("precio_total"); //precio del producto * cantidad de productos

                producto = new Producto(codigoProducto, costo, cantidadProducto);
                productos.add(producto);

            }

            listadoProductos.setListadoProductos(productos);

        } catch (SQLException e) {
            e.printStackTrace(System.out);

        } finally {

            DBConectionManager.close(connection);
            DBConectionManager.close(resultSet);
            DBConectionManager.close(preparedStatement);

        }

        return listadoProductos;
    }

}
