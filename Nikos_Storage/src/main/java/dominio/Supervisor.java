/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio;

/**
 *
 * @author sirbon
 */
public class Supervisor extends Usuario {

    public Supervisor() {
    }

    public Supervisor(int codigo) {
        super(codigo);
    }

    public Supervisor(int codigo, String nombre, String username, String password, String email) {
        super(codigo, nombre, username, password, email);
    }

    public Supervisor(String nombre, String apellido, String username, String password, String email) {
        super(nombre, apellido, username, password, email);
    }

}
