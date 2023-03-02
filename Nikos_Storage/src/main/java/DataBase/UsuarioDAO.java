/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataBase;

import java.util.*;
import dominio.*;
import java.sql.*;

/**
 *
 * @author sirbon
 */
public class UsuarioDAO {

    public List<Usuario> listarUsuarios(String table) {

        final String SQL_SELECT = "SELECT codigo_administrador, nombre_administrador, apellido_administrador, nick_administrador"
                + "password_administsrador, email_administrador from " + table;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Usuario usuario = null;
        List<Usuario> usuarios = new ArrayList<>();

        try {
            connection = DBConectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SQL_SELECT);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int codigo = resultSet.getInt("codigo_administrador");
                String nombre = resultSet.getString("nombre");
                String apellido = resultSet.getString("apellido");
                String nick = resultSet.getString("nick");
                String password = resultSet.getString("user_password");
                String email = resultSet.getString("email");

                Usuario Usuario = new Usuario(nombre, apellido, nick, password, email);
                usuarios.add(Usuario);

            }

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {

            DBConectionManager.close(connection);
            DBConectionManager.close(resultSet);
            DBConectionManager.close(preparedStatement);

        }

        return usuarios;
    }
    
    public Usuario buscarUsuario(Usuario usuario, String table) {

        final String SQL_SELECT_BY_ID = "SELECT codigo, nombre, apellido, "
                + "nick, user_password, email from " + table + " WHERE codigo = ?";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SQL_SELECT_BY_ID);
            preparedStatement.setInt(0, usuario.getCodigo());

            resultSet = preparedStatement.executeQuery();

            String nombre = resultSet.getString("nombre");
            usuario.setNombre(nombre);
            String apellido = resultSet.getString("apellido");
            usuario.setApellido(apellido);
            String nick = resultSet.getString("nick");
            usuario.setNickName(nick);
            String password = resultSet.getString("user_password");
            usuario.setPassword(password);
            String email = resultSet.getString("email");
            usuario.setEmail(email);

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {

            DBConectionManager.close(connection);
            DBConectionManager.close(resultSet);
            DBConectionManager.close(preparedStatement);

        }
        return usuario;
    }

    public int insertUsuario(Usuario usuario, String table) {

        final String SQL_INSERT = "INSERT INTO" + table + " (nombre, apellido,"
                + " nick, user_password, email) VALUES (?, ?, ?, ?, ?)";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int rowsAfected = 0;

        try {
            connection = DBConectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SQL_INSERT);

            preparedStatement.setString(1, usuario.getNombre());
            preparedStatement.setString(2, usuario.getApellido());
            preparedStatement.setString(3, usuario.getNickName());
            preparedStatement.setString(4, usuario.getPassword());
            preparedStatement.setString(5, usuario.getEmail());

            rowsAfected = preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {

            DBConectionManager.close(connection);
            DBConectionManager.close(preparedStatement);

        }

        return rowsAfected;
    }

    public int actualizarUsuario(Usuario usuario, String table) {
        final String SQL_UPDATE = "UPDATE " + table + " SET nombre=?, apellido=?, "
                + "nick=? WHERE codigo=?";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int rowsAfected = 0;

        try {
            connection = DBConectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SQL_UPDATE);

            preparedStatement.setString(1, usuario.getNombre());
            preparedStatement.setString(2, usuario.getApellido());
            preparedStatement.setString(3, usuario.getNickName());
            preparedStatement.setInt(4, usuario.getCodigo());

            rowsAfected = preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {

            DBConectionManager.close(connection);
            DBConectionManager.close(preparedStatement);

        }

        return rowsAfected;
    }

    public int eliminarUsuario(Usuario usuario, String table) {

        final String SQL_DELETE = "DELETE FROM " + table + " WHERE codigo_administrador=?";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int rowsAfected = 0;

        try {
            connection = DBConectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SQL_DELETE);

            preparedStatement.setInt(4, usuario.getCodigo());

            rowsAfected = preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {

            DBConectionManager.close(connection);
            DBConectionManager.close(preparedStatement);

        }

        return rowsAfected;

    }

}
