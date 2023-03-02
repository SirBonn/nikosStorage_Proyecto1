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
public class Usuario {

    private int codigo;
    private int nivelAcceso;
    private String nombre;
    private String apellido;
    private String nickName;
    private String password;
    private String email;

    public Usuario() {
    }
 
    public Usuario(int codigo) {
        this.codigo = codigo;
    }

    public Usuario(int codigo, int levelUsr, String nombre, String apellido, String nickName, String password, String email) {
        this.codigo = codigo;
        this.nivelAcceso = levelUsr;
        this.nombre = nombre;
        this.apellido = apellido;
        this.nickName = nickName;
        this.password = password;
        this.email = email;
    }
    
    public Usuario(int levelUsr, String nombre, String apellido, String nickName, String password, String email) {
        this.nivelAcceso = levelUsr;
        this.nombre = nombre;
        this.apellido = apellido;
        this.nickName = nickName;
        this.password = password;
        this.email = email;
    }

    public Usuario(String nombre, String apellido, String nickName, String password, String email) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.nickName = nickName;
        this.password = password;
        this.email = email;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getLevelUsr() {
        return nivelAcceso;
    }

    public void setLevelUsr(int levelUsr) {
        this.nivelAcceso = levelUsr;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Usuario{" + "\ncodigo=" + codigo + "\nnombre=" + nombre + "\napellido=" + apellido + "\nnickName=" 
                + nickName + "\npassword=" + password + "\nemail=" + email + '}';
    }

}
