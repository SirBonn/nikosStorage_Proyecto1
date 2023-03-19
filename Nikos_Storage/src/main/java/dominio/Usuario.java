/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio;

import security.PasswordEncoder;

/**
 *
 * @author sirbon
 */
public class Usuario {

    private int codigo;
    private int nivelAcceso = -1;
    private String nombre;
    private String apellido;
    private String username;
    private String password;
    private String email;
    private int tienda;

    public Usuario() {
    }

    public Usuario(int codigo) {
        this.codigo = codigo;
    }

    public Usuario(String nombre, String username, String password) {
        this.nombre = nombre;
        this.username = username;
        this.password = PasswordEncoder.encodePassword(password);
    }

    public Usuario(String username, String password) {
        this.username = username;
        this.password = PasswordEncoder.encodePassword(password);
    }

    public Usuario(int codigo, int levelUsr, String nombre, String apellido, String username, String password, String email, int tiendaKey) {
        this.codigo = codigo;
        this.nivelAcceso = levelUsr;
        this.nombre = nombre;
        this.apellido = apellido;
        this.username = username;
        this.password = PasswordEncoder.encodePassword(password);
        this.email = email;
        this.tienda = tiendaKey;
    }

    public Usuario(int codigo, String nombre, String username, String password, String email) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.username = username;
        this.password = PasswordEncoder.encodePassword(password);
        this.email = email;
    }

    public Usuario(int codigo, String nombre, String username, String password) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.username = username;
        this.password = PasswordEncoder.encodePassword(password);
    }

    public Usuario(int codigo, String nombre, String username, String password, String email, int tiendaKey) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.username = username;
        this.password = PasswordEncoder.encodePassword(password);
        this.email = email;
        this.tienda = tiendaKey;
    }

    public Usuario(String nombre, String apellido, String username, String password, String email) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.username = username;
        this.password = PasswordEncoder.encodePassword(password);
        this.email = email;
    }

    public Usuario(String nombre, String apellido, String username, String password, String email, int tienda) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.username = username;
        this.password = PasswordEncoder.encodePassword(password);
        this.email = email;
        this.tienda = tienda;
    }

    public Usuario(int codigo, int levelUsr, String nombre, String apellido, String username, String password, String email) {
        this.codigo = codigo;
        this.nivelAcceso = levelUsr;
        this.nombre = nombre;
        this.apellido = apellido;
        this.username = username;
        this.password = PasswordEncoder.encodePassword(password);
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
        return username;
    }

    public void setNickName(String nickName) {
        this.username = nickName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = PasswordEncoder.encodePassword(password);;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTienda() {
        return tienda;
    }

    public void setTienda(int tiendaKey) {
        this.tienda = tiendaKey;
    }

    @Override
    public String toString() {
        return "Usuario{" + "\ncodigo=" + codigo + "\nnivelAcceso=" + nivelAcceso + "\nnombre=" + nombre
                + "\napellido=" + apellido + "\nnickName=" + username + "\npassword=" + password + "\nemail=" + email
                + "\ntiendaKey=" + tienda + '}';
    }

}
