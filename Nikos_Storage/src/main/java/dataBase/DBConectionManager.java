package dataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConectionManager {

    private static final String USER = "sirRoot";
    private static final String PASSWORD = "sirRoot8989_";
    private static final String URL = "jdbc:mysql://localhost:3306/nikos_storage";

    public static Connection getConnection() {
        
        Connection connection = null;
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("se conecto con exito a la db");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error en la conexion: " +e);
            
        }
        
        return connection;
    }

    public static void close(Connection conecction){
        try {
            conecction.close();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
    }
    
    public static void close(ResultSet resultSet){
        try {
            resultSet.close();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
    }
    
    public static void close(PreparedStatement preparedStatement){
        try {
            preparedStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
        
        
    }
}
