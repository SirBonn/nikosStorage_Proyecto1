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
    private ArrayList<Producto> productos = new ArrayList<>();
    private String motivoDevolucion;
    private int codigoProductoDevuelto;

    public ReclamoDevolucion() {
    }

    public ReclamoDevolucion(int codigoDevolucion) {
        this.codigoDevolucion = codigoDevolucion;
    }

    public ReclamoDevolucion(int codigoDevolucion, String motivoDevolucion) {
        this.codigoDevolucion = codigoDevolucion;
        this.motivoDevolucion = motivoDevolucion;
    }
    
    public ReclamoDevolucion(int codigoDevolucion, String motivoDevolucion, Bodeguero bodegueroEncargado, int codigoProductoDevuelto) {
        this.codigoDevolucion = codigoDevolucion;
        this.motivoDevolucion = motivoDevolucion;
        this.codigoProductoDevuelto = codigoProductoDevuelto;
    }

    public void agregarProducto(Producto producto) {
        this.productos.add(producto);
    }
    
}
