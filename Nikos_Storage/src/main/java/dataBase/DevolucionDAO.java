/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataBase;

import dominio.Devolucion;
import dominio.Envio;
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
public class DevolucionDAO {

    private String informe = "";
    
    public int insertarDevolucion(Devolucion devolucion) {

        final String SQL_INSERT = "INSERT INTO DEVOLUCIONES  (codigo_devolucion, fecha_devolucion, estado_devolucion, "
                + "total_devuelto, envio_devuelto, bodeguero_encargado) VALUES (?, ?, ?, ?, ?, ?)";

        int rowAffected = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DBConectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SQL_INSERT);

            preparedStatement.setInt(1, devolucion.getCodigoDevolucion());
            preparedStatement.setString(2, devolucion.getTimestampDevolucion());
            preparedStatement.setString(3, devolucion.getEstadoDevolucion());
            preparedStatement.setDouble(4, devolucion.getTotalDevuelto());
            preparedStatement.setInt(5, devolucion.getEnvioDevuelto().getCodigoEnvio());
            preparedStatement.setInt(6, devolucion.getEncargado());

            rowAffected = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("error al agregar una devolucion \n" + e);
        } finally {

                System.out.println("se agrego un devolucion correctamente");
            DBConectionManager.close(connection);
            DBConectionManager.close(preparedStatement);

        }

        return rowAffected;

    }

    public List<Devolucion> listarDevoluciones() {

        final String SQL_SELECT = "SELECT codigo_devolucion, fecha_devolucion, estado_devolucion, "
                + "total_devuelto, envio_devuelto, bodeguero_encargado FROM DEVOLUCIONES";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Devolucion devolucion = null;
        List<Devolucion> devoluciones = new ArrayList<>();

        try {
            connection = DBConectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SQL_SELECT);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int codigo = resultSet.getInt("codigo_devolucion");
                String fecha = resultSet.getString("fecha_devolucion");
                String estado = resultSet.getString("estado_devolucion");
                double total = resultSet.getDouble("total_devuelto");
                Usuario bodegueroEncargado = new Usuario(resultSet.getInt("bodeguero_encargado"));
                int codigoEnvio =resultSet.getInt("envio_devuelto");
                 Envio envio = new EnvioDAO().buscarEnvio(new Envio(codigoEnvio));

               devolucion = new Devolucion(codigo, fecha, estado, total, envio, bodegueroEncargado);
               
               devoluciones.add(devolucion);
               
            }

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
            this.informe += ex.getMessage() +"\n";
            
        } finally {

            DBConectionManager.close(connection);
            DBConectionManager.close(resultSet);
            DBConectionManager.close(preparedStatement);

        }

        return devoluciones;
    }
    
}
