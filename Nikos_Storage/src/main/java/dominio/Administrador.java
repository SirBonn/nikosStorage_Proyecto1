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
public class Administrador  extends Usuario{

    public Administrador() {
    }

    public Administrador(int codigo) {
        super(codigo);
    }

    public Administrador(int codigo, int levelUsr, String nombre, String apellido, String nickName, String password, String email) {
        super(codigo, levelUsr, nombre, apellido, nickName, password, email);
    }

    public Administrador(int levelUsr, String nombre, String apellido, String nickName, String password, String email) {
        super(levelUsr, nombre, apellido, nickName, password, email);
    }

    public Administrador(String nombre, String apellido, String nickName, String password, String email) {
        super(nombre, apellido, nickName, password, email);
    }
    
}
