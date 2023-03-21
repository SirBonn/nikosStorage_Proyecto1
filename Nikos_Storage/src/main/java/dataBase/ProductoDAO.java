/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataBase;

import dominio.Producto;
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
public class ProductoDAO {

    private String informe ="";
    
    public List<Producto> listarProductos() {

        final String SQL_SELECT = "SELECT codigo_producto, nombre_producto, precio_costo_producto, "
                + "precio_venta_producto, existencia_producto FROM PRODUCTOS";

        List<Producto> productos = new ArrayList<>();
        Producto producto;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SQL_SELECT);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int codigo = resultSet.getInt("codigo_producto");
                String nombre = resultSet.getString("nombre_producto");
                double costo = resultSet.getDouble("precio_costo_producto");
                double precio = resultSet.getDouble("precio_venta_producto");
                int existencia = resultSet.getInt("existencia_producto");

                producto = new Producto(codigo, nombre, costo, precio, existencia);
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

    public Producto buscarProducto(Producto producto) {
        final String SQL_SELECT_BY_ID = "SELECT nombre_producto, precio_costo_producto, precio_venta_producto, existencia_producto FROM PRODUCTOS WHERE codigo_producto=?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SQL_SELECT_BY_ID);
            preparedStatement.setInt(1, producto.getCodigo());

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                
                String nombre = resultSet.getString("nombre_producto");
                producto.setNombre(nombre);
                double costo = resultSet.getDouble("precio_costo_producto");
                producto.setPrecioCosto(costo);
                double venta = resultSet.getDouble("precio_venta_producto");
                producto.setPrecioVenta(venta);
                int existencia = resultSet.getInt("existencia_producto");
                producto.setCantidad(existencia);
                
            }

        } catch (SQLException e) {
            this.informe += e +"\n";
            e.printStackTrace(System.out);

        } finally {

            DBConectionManager.close(connection);
            DBConectionManager.close(resultSet);
            DBConectionManager.close(preparedStatement);

        }

        return producto;
    }

    public int insertarProducto(Producto producto) {

        final String SQL_INSERT = "INSERT INTO PRODUCTOS  (codigo_producto, nombre_producto, precio_costo_producto, "
                + "precio_venta_producto, existencia_producto) VALUES (?, ?, ?, ?, ?)";

        int rowAffected = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DBConectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SQL_INSERT);

            preparedStatement.setInt(1, producto.getCodigo());
            preparedStatement.setString(2, producto.getNombre());
            preparedStatement.setDouble(3, producto.getPrecioCosto());
            preparedStatement.setDouble(4, producto.getPrecioVenta());
            preparedStatement.setInt(5, producto.getCantidad());

            rowAffected = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("error al agregar un producto\n" + e);
        } finally {

            System.out.println("se agrego un producto correctamente");
            DBConectionManager.close(connection);
            DBConectionManager.close(preparedStatement);

        }

        return rowAffected;

    }

    public int actualizarProducto(Producto producto) {
        final String SQL_UPDATE = "UPDATE PRODUCTOS SET nombre_producto = ?, precio_costo_producto = ?, "
                + "precio_venta_producto = ?, existencia_producto = ? WHERE codigo_producto=?";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int rowsAfected = 0;

        try {
            connection = DBConectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SQL_UPDATE);

            preparedStatement.setString(1, producto.getNombre());
            preparedStatement.setDouble(2, producto.getPrecioCosto());
            preparedStatement.setDouble(3, producto.getPrecioVenta());
            preparedStatement.setInt(4, producto.getCantidad());
            preparedStatement.setInt(5, producto.getCodigo());

            rowsAfected = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace(System.out);
        } finally {

            DBConectionManager.close(connection);
            DBConectionManager.close(preparedStatement);

        }

        return rowsAfected;
    }

    public int eliminarProducto(Producto producto) {

        final String SQL_DELETE = "DELETE FROM PRODUCTOS WHERE codigo_producto=?";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int rowsAfected = 0;

        try {
            connection = DBConectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SQL_DELETE);

            preparedStatement.setInt(1, producto.getCodigo());

            rowsAfected = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace(System.out);
        } finally {

            DBConectionManager.close(connection);
            DBConectionManager.close(preparedStatement);

        }

        return rowsAfected;

    }

    public List<Producto> getTopProductosPedidos(){

        final String SQL_SELECT = "SELECT PRODUCTOS.codigo_producto, COUNT(*) AS totalProductos FROM "
                + "PRODUCTOS JOIN LISTADO_PRODUCTOS ON PRODUCTOS.codigo_producto = LISTADO_PRODUCTOS.codigo_producto"
                + " GROUP BY PRODUCTOS.codigo_producto ORDER BY totalProductos DESC";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Producto producto = null;
        List<Producto> productos = new ArrayList<>();

        try {
            connection = DBConectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SQL_SELECT);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int codigo = resultSet.getInt("PRODUCTOS.codigo_producto");
                producto = new ProductoDAO().buscarProducto(new Producto(codigo));
                int cantidad = resultSet.getInt("totalProductos");
                producto.setCantidad(cantidad);
                productos.add(producto);
            }

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
            this.informe = ex.getMessage();

        } finally {

            DBConectionManager.close(connection);
            DBConectionManager.close(resultSet);
            DBConectionManager.close(preparedStatement);

        }

        return productos;
    }
    
}
