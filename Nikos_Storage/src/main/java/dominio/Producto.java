/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Producto {

    private int codigo;
    private String nombre;
    private double precioCosto;
    private double precioVenta;
    private int cantidad;

    public Producto(int codigo) {
        this.codigo = codigo;
    }

    
    public Producto(int codigo, String nombre, double precioCosto, double precioVenta, int existencias) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precioCosto = precioCosto;
        this.precioVenta = precioVenta;
        this.cantidad = existencias;
    }

    public Producto(int codigo, int cantidad) {
        this.codigo = codigo;
        this.cantidad = cantidad;
    }
    
    
    public Producto(String nombre, double precioCosto, double precioVenta, int existencias) {
        this.nombre = nombre;
        this.precioCosto = precioCosto;
        this.precioVenta = precioVenta;
        this.cantidad = existencias;
    }

    public Producto(int codigo, double precioVenta, int cantidad) {
        this.codigo = codigo;
        this.precioVenta = precioVenta;
        this.cantidad = cantidad;
    }
    
    
    
    public double getTotal(){
        return (this.cantidad * this.precioVenta);
    }
    
    
}
