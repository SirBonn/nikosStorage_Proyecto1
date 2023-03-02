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
    
    int tiendaAsignada;
    
    public Dependiente() {
    }

    public Dependiente(int codigo) {
        super(codigo);
    }

    public Dependiente(int tiendaAsignada, int codigo, int levelUsr, String nombre, String apellido, String nickName, String password, String email) {
        super(codigo, levelUsr, nombre, apellido, nickName, password, email);
        this.tiendaAsignada = tiendaAsignada;
    }

    public Dependiente(int tiendaAsignada, int levelUsr, String nombre, String apellido, String nickName, String password, String email) {
        super(levelUsr, nombre, apellido, nickName, password, email);
        this.tiendaAsignada = tiendaAsignada;
    }

    public Dependiente(int tiendaAsignada, String nombre, String apellido, String nickName, String password, String email) {
        super(nombre, apellido, nickName, password, email);
        this.tiendaAsignada = tiendaAsignada;
    }   

    
}
