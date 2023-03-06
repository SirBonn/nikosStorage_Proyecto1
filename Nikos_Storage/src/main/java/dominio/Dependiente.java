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
public class Dependiente extends Usuario {

    public Dependiente() {
    }

    public Dependiente(int codigo) {
        super(codigo);
    }

    public Dependiente(int codigo, String nombre, String username, String password, String email, int tiendaKey) {
        super(codigo, nombre, username, password, email, tiendaKey);
    }

    public Dependiente(int tiendaAsignada, String nombre, String apellido, String nickName, String password, String email) {
        super(nombre, apellido, nickName, password, email);
    }

}
