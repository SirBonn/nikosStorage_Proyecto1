/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataBase;

import dominio.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author sirbon
 */
public class ListadoProductosDAO {

    public int insertarProducto(ListadoProductos lIstadoProductos) {

        final String SQL_INSERT = "INSERT INTO LISTADO_PRODUCTOS  (codigo_pedido, codigo_producto, cantidad, "
                + "precio_costo, precio_total) VALUES (?, ?, ?, ?, ?)";

        int rowAffected = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DBConectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SQL_INSERT);
            ArrayList<Producto> productos = lIstadoProductos.getListadoProductos();
           
            for (Producto producto : productos) {

                preparedStatement.setInt(1, lIstadoProductos.getCodigoListado());
                preparedStatement.setInt(2, producto.getCodigo());
                preparedStatement.setInt(3, producto.getCantidad());
                preparedStatement.setDouble(4, producto.getPrecioVenta());
                preparedStatement.setDouble(5, producto.getTotal());
                rowAffected = preparedStatement.executeUpdate();
            }

        } catch (SQLException e) {
            System.out.println("error al agregar un producto\n" + e);
        } finally {

            System.out.println("se agrego un producto correctamente");
            DBConectionManager.close(connection);
            DBConectionManager.close(preparedStatement);

        }

        return rowAffected;

    }
}
