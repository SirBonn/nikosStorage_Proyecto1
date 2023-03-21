/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio;

import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author sirbon
 */
@ToString
@Getter
@Setter
public class ReclamoDevolucion {

    private int codigoDevolucion; //referencia a codigoDevolucion en devoluciones
    private Producto productoDevuelto;
    private String motivoDevolucion;

    public ReclamoDevolucion() {
    }

    public ReclamoDevolucion(int codigoDevolucion) {
        this.codigoDevolucion = codigoDevolucion;
    }

    
    
    public ReclamoDevolucion(int codigoDevolucion, String motivoDevolucion) {
        this.codigoDevolucion = codigoDevolucion;
        this.motivoDevolucion = motivoDevolucion;
    }
    
    public ReclamoDevolucion(int codigoDevolucion, String motivoDevolucion, Producto productoDevuelto) {
        this.codigoDevolucion = codigoDevolucion;
        this.motivoDevolucion = motivoDevolucion;
        this.productoDevuelto = productoDevuelto;
    }

//    public void agregarProducto(Producto producto) {
//        this.productos.add(producto);
//    }
    
}
