/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataBase;

import java.util.*;
import dominio.*;
import java.sql.*;
import lombok.Getter;

/**
 *
 * @author sirbon
 */
@Getter
public class UsuarioDAO {

    private String informe = null;

    public List<Usuario> listarUsuarios(String table) {

        final String SQL_SELECT = userSelect(table, false);

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
                int codigo = resultSet.getInt("codigo");
                String nombre = resultSet.getString("nombre");
                String apellido = resultSet.getString("apellido");
                String nick = resultSet.getString("nick");
                String password = resultSet.getString("user_password");
                String email = resultSet.getString("email");
                int lvlUsr = resultSet.getInt("nivel_usuario");

                if (table.equals("DEPENDIENTES") || table.equals("BODEGUEROS")) {
                    int tienda = resultSet.getInt("tienda_asignada");
                    usuario = new Usuario(codigo, lvlUsr, nombre, apellido, nick, password, email, tienda);
                    usuarios.add(usuario);
                } else {
                    usuario = new Usuario(codigo, lvlUsr, nombre, apellido, nick, password, email);
                    usuarios.add(usuario);
                }

            }

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
            this.informe = ex.getMessage();

        } finally {

            DBConectionManager.close(connection);
            DBConectionManager.close(resultSet);
            DBConectionManager.close(preparedStatement);

        }

        return usuarios;
    }

    public Usuario buscarUsuario(Usuario usuario, String table) {

        String SQL_SELECT_BY_ID = userSelect(table, false) + " WHERE codigo = ?";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SQL_SELECT_BY_ID);
            preparedStatement.setInt(1, usuario.getCodigo());

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
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
                int lvlUsr = resultSet.getInt("nivel_usuario");
                usuario.setLevelUsr(lvlUsr);

                if (table.equals("DEPENDIENTES") || table.equals("BODEGUEROS")) {
                    int tienda = resultSet.getInt("tienda_asignada");
                    usuario.setTienda(tienda);
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
            this.informe = ex.getMessage();
        } finally {

            DBConectionManager.close(connection);
            DBConectionManager.close(resultSet);
            DBConectionManager.close(preparedStatement);

        }

        return usuario;
    }

    public List<Usuario> listarUsuariosTienda(int tienda) {

        final String SQL_SELECT = "SELECT codigo, nombre, apellido, "
                + "nick, user_password, email, nivel_usuario FROM BODEGUEROS WHERE tienda_asignada = ?";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Usuario usuario = null;
        List<Usuario> usuarios = new ArrayList<>();

        try {
            connection = DBConectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SQL_SELECT);
            preparedStatement.setInt(1, tienda);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int codigo = resultSet.getInt("codigo");
                String nombre = resultSet.getString("nombre");
                String apellido = resultSet.getString("apellido");
                String nick = resultSet.getString("nick");
                String password = resultSet.getString("user_password");
                String email = resultSet.getString("email");
                int lvlUsr = resultSet.getInt("nivel_usuario");
                usuario = new Usuario(codigo, lvlUsr, nombre, apellido, nick, password, email);
                usuarios.add(usuario);
            }

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
            this.informe = ex.getMessage();

        } finally {

            DBConectionManager.close(connection);
            DBConectionManager.close(resultSet);
            DBConectionManager.close(preparedStatement);

        }

        return usuarios;
    }

    public Usuario logearUsuario(Usuario usuario) {

        List<String> tables = Arrays.asList("ADMINISTRADORES", "DEPENDIENTES", "BODEGUEROS", "SUPERVISORES");
        boolean isUsuario = false;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConectionManager.getConnection();

            for (String table : tables) {

                String SQL_SELECT_BY_NAME = userSelect(table, false) + " WHERE nick = ? AND user_password=?";
                preparedStatement = connection.prepareStatement(SQL_SELECT_BY_NAME);
                preparedStatement.setString(1, usuario.getNickName());
                preparedStatement.setString(2, usuario.getPassword());
                resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    int codigo = resultSet.getInt("codigo");
                    usuario.setCodigo(codigo);
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
                    int lvlUsr = resultSet.getInt("nivel_usuario");
                    usuario.setLevelUsr(lvlUsr);

                    if (table.equals("DEPENDIENTES") || table.equals("BODEGUEROS")) {
                        int tienda = resultSet.getInt("tienda_asignada");
                        usuario.setTienda(tienda);
                    }

                }

            }

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
            this.informe = ex.getMessage();

        } finally {

            DBConectionManager.close(connection);
            DBConectionManager.close(resultSet);
            DBConectionManager.close(preparedStatement);

        }

        return usuario;
    }

    public int insertUsuario(Usuario usuario, String table) {

        final String SQL_INSERT = userSelect(table, true);

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
            if (table.equals("DEPENDIENTES") || table.equals("BODEGUEROS")) {
                preparedStatement.setInt(6, usuario.getTienda());

            }
            System.out.println("se agregro un usuario correctamente a " +table);

            rowsAfected = preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("error al agregar un usuario");
            ex.printStackTrace(System.out);
            this.informe = ex.getMessage();

        } finally {
            DBConectionManager.close(connection);
            DBConectionManager.close(preparedStatement);

        }

        return rowsAfected;
    }

    public int insertAdmin(Usuario usuario, String table) {

        final String SQL_INSERT = "INSERT INTO " + table + " (nombre, apellido,"
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
        String SQL_UPDATE;
        boolean haveTienda = true;
        if (table.equals("DEPENDIENTES") || table.equals("BODEGUEROS")) {
            SQL_UPDATE = "UPDATE " + table + " SET nombre=?, apellido=?, email=?, nick=?, "
                    + " tienda_asignada=? WHERE codigo=?";
        } else {
            SQL_UPDATE = "UPDATE " + table + " SET nombre=?, apellido=?, email=?,"
                    + "nick=? WHERE codigo=?";
            haveTienda = false;
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int rowsAfected = 0;

        try {
            connection = DBConectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SQL_UPDATE);

            preparedStatement.setString(1, usuario.getNombre());
            preparedStatement.setString(2, usuario.getApellido());
            preparedStatement.setString(3, usuario.getEmail());
            preparedStatement.setString(4, usuario.getNickName());

            if (haveTienda) {
                preparedStatement.setInt(5, usuario.getTienda());
                preparedStatement.setInt(6, usuario.getCodigo());
            } else {
                preparedStatement.setInt(5, usuario.getCodigo());

            }

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

        final String SQL_DELETE = "DELETE FROM " + table + " WHERE codigo=?";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int rowsAfected = 0;

        try {
            connection = DBConectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SQL_DELETE);

            preparedStatement.setInt(1, usuario.getCodigo());

            rowsAfected = preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
            this.informe = ex.getMessage();

        } finally {

            DBConectionManager.close(connection);
            DBConectionManager.close(preparedStatement);

        }

        return rowsAfected;

    }

    private String userSelect(String table, boolean isInsert) {
        if (table.equals("DEPENDIENTES") || table.equals("BODEGUEROS")) {
            if (isInsert) {
                return "INSERT INTO " + table + " (nombre, apellido,"
                        + " nick, user_password, email, tienda_asignada) VALUES (?, ?, ?, ?, ?, ?)";
            } else {
                return "SELECT codigo, nombre, apellido, "
                        + "nick, user_password, email, nivel_usuario, tienda_asignada from " + table;
            }

        } else {
            if (isInsert) {
                return "INSERT INTO " + table + " (nombre, apellido,"
                        + " nick, user_password, email) VALUES (?, ?, ?, ?, ?)";
            } else {

                return "SELECT codigo, nombre, apellido, "
                        + "nick, user_password, email, nivel_usuario from " + table;
            }
        }

    }

}
