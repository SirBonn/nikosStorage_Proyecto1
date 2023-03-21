/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataBase;

import dominio.Producto;
import dominio.ReclamoDevolucion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author sirbon
 */
public class ReclamoDevolucionDAO {

    public int insertarReclamo(ReclamoDevolucion reclamoDevolucion) {

        final String SQL_INSERT = "INSERT INTO PRODUCTOS_DEVUELTOS  (codigo_devolucion, codigo_producto_devuelto, cantidad_devuelta, precio_costo_devuelto, "
                + "precio_total_devuelto, motivo_devolucion) VALUES (?, ?, ?, ?, ?, ?)";

        int rowAffected = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DBConectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SQL_INSERT);
            Producto producto = reclamoDevolucion.getProductoDevuelto();

            preparedStatement.setInt(1, reclamoDevolucion.getCodigoDevolucion());
            preparedStatement.setInt(2, producto.getCodigo());
            preparedStatement.setInt(3, producto.getCantidad());
            preparedStatement.setDouble(4, producto.getPrecioVenta());
            preparedStatement.setDouble(5, producto.getTotal());
            preparedStatement.setString(6, reclamoDevolucion.getMotivoDevolucion());

            rowAffected = preparedStatement.executeUpdate();
            System.out.println("se agrego un reclamo correctamente");

        } catch (SQLException e) {
            System.out.println("error al agregar un reclamo\n" + e);
        } finally {

            DBConectionManager.close(connection);
            DBConectionManager.close(preparedStatement);

        }

        return rowAffected;

    }

}
