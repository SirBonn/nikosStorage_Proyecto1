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
    private int existencias;

    public Producto(int codigo) {
        this.codigo = codigo;
    }

    
    public Producto(int codigo, String nombre, double precioCosto, double precioVenta, int existencias) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precioCosto = precioCosto;
        this.precioVenta = precioVenta;
        this.existencias = existencias;
    }

    public Producto(int codigo, int existencias) {
        this.codigo = codigo;
        this.existencias = existencias;
    }
    
    
    
    public Producto(String nombre, double precioCosto, double precioVenta, int existencias) {
        this.nombre = nombre;
        this.precioCosto = precioCosto;
        this.precioVenta = precioVenta;
        this.existencias = existencias;
    }

    
    
    
}
