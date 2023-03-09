/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataBase;

import dominio.Producto;
import dominio.Tienda;
import dataBase.ProductoDAO;
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

    ProductoDAO productoDAO = new ProductoDAO();

    public List<Producto> listarCatalogo(Tienda tienda) {

        final String SQL_SELECT = "SELECT codigo_producto, existencia_producto,  FROM "
                + "CATALOGO WHERE codigo_tienda=?";
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
                producto.setExistencias(resultSet.getInt("existencia_producto"));

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

}
