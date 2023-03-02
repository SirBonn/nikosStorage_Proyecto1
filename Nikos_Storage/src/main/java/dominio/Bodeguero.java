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
public class Bodeguero extends Usuario {

    public Bodeguero() {
    }

    public Bodeguero(int codigo) {
        super(codigo);
    }

    public Bodeguero(int codigo, int levelUsr, String nombre, String apellido, String nickName, String password, String email) {
        super(codigo, levelUsr, nombre, apellido, nickName, password, email);
    }

    public Bodeguero(int levelUsr, String nombre, String apellido, String nickName, String password, String email) {
        super(levelUsr, nombre, apellido, nickName, password, email);
    }

    public Bodeguero(String nombre, String apellido, String nickName, String password, String email) {
        super(nombre, apellido, nickName, password, email);
    }
    
    
}
