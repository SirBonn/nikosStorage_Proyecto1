/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataBase;

/**
 *
 * @author sirbon
 */
public class tmpMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       DBConectionManager dBConectionManager = new DBConectionManager();
        DBConectionManager.getConnection();
    }
    
}
