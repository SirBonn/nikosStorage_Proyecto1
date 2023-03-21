/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataBase;

import dominio.Envio;
import dominio.Incidencia;
import dominio.Producto;
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
public class IncidenciaDAO {

    private String informe = "";

    public int insertarIncidencia(Incidencia incidencia) {

        final String SQL_INSERT = "INSERT INTO INCIDENCIAS  (codigo_incidencia, fecha_incidencia, estado_incidencia, "
                + "envio_incidente, bodeguero_encargado) VALUES (?, ?, ?, ?, ?)";

        int rowAffected = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DBConectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SQL_INSERT);

            preparedStatement.setInt(1, incidencia.getCodigo());
            preparedStatement.setString(2, incidencia.getDateIncidencia());
            preparedStatement.setString(3, incidencia.getEstadoIncidencia());
            preparedStatement.setInt(4, incidencia.getEnvioDevuelto().getCodigoEnvio());
            preparedStatement.setInt(5, incidencia.getEncargado());

            rowAffected = preparedStatement.executeUpdate();
            System.out.println("se agrego un incidencia correctamente");

        } catch (SQLException e) {
            System.out.println("error al agregar una incidencai \n" + e);
        } finally {

            DBConectionManager.close(connection);
            DBConectionManager.close(preparedStatement);

        }

        return rowAffected;

    }

    public List<Incidencia> listarIncidencias() {

        final String SQL_SELECT = "SELECT codigo_incidencia, fecha_incidencia, estado_incidencia, "
                + "envio_incidente, bodeguero_encargado FROM INCIDENCIAS";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Incidencia incidencia = null;
        List<Incidencia> incidencias = new ArrayList<>();

        try {
            connection = DBConectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SQL_SELECT);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int codigo = resultSet.getInt("codigo_incidencia");
                String fecha = resultSet.getString("fecha_incidencia");
                String estado = resultSet.getString("estado_incidencia");
                Usuario bodegueroEncargado = new Usuario(resultSet.getInt("bodeguero_encargado"));
                Envio envio = new EnvioDAO().buscarEnvio(new Envio(resultSet.getInt("envio_incidente")));

                incidencia = new Incidencia(codigo, fecha, estado, bodegueroEncargado, envio);

                incidencias.add(incidencia);

            }

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
            this.informe += ex.getMessage() + "\n";

        } finally {

            DBConectionManager.close(connection);
            DBConectionManager.close(resultSet);
            DBConectionManager.close(preparedStatement);

        }

        return incidencias;
    }

    public Incidencia buscarIncidencia(Incidencia incidencia) {

        final String SQL_SELECT_BY_ID = "SELECT fecha_incidencia, estado_incidencia, "
                + "envio_incidente, bodeguero_encargado FROM INCIDENCIAS WHERE codigo_incidencia=?";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SQL_SELECT_BY_ID);
            preparedStatement.setInt(1, incidencia.getCodigo());
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                incidencia.setFechaIncidencia(incidencia.setNowDate());
                String estado = resultSet.getString("estado_incidencia");
                incidencia.setEstadoIncidencia(estado);
                int codigoEnvio = resultSet.getInt("envio_incidente");
                Envio envio = new EnvioDAO().buscarEnvio(new Envio(codigoEnvio));

                incidencia.setEnvioDevuelto(envio);
                Usuario encargado = new UsuarioDAO().buscarUsuario(new Usuario(resultSet.getInt("bodeguero_encargado")), "BODEGUEROS");
                incidencia.setEncargado(encargado);

            }

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
            this.informe += ex.getMessage() + "\n";

        } finally {

            DBConectionManager.close(connection);
            DBConectionManager.close(resultSet);
            DBConectionManager.close(preparedStatement);

        }

        return incidencia;
    }

    public int actualizarIncidencia(Incidencia incidencia) {

        final String SQL_UPDATE = "UPDATE INCIDENCIAS SET estado_incidencia = ?, estado_incidencia =? "
                + "WHERE codigo_incidencia=?";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int rowsAffected = 0;

        try {
            connection = DBConectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SQL_UPDATE);

            preparedStatement.setString(1, incidencia.getEstadoIncidencia());
            preparedStatement.setString(2, incidencia.getSolucion());
            preparedStatement.setInt(3, incidencia.getCodigo());

            rowsAffected = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace(System.out);
        } finally {

            DBConectionManager.close(connection);
            DBConectionManager.close(preparedStatement);

        }

        return rowsAffected;
    }

    public List<Producto> getTopProductosInc() {

        final String SQL_SELECT = "SELECT PRODUCTOS.codigo_producto, COUNT(*) AS totalProductos FROM "
                + "PRODUCTOS JOIN RECLAMOS_INCIDENCIAS ON PRODUCTOS.codigo_producto = RECLAMOS_INCIDENCIAS.codigo_producto_incidente "
                + "GROUP BY PRODUCTOS.codigo_producto ORDER BY totalProductos DESC";

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
