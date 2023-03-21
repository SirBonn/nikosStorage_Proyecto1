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
public class ReclamoIncidencia {

    private int codigoIncidencia;
    private Producto producto;
    private String motivoIncidencia;
    private Usuario bodegueroEncargado;

    public ReclamoIncidencia() {
    }

    public ReclamoIncidencia(int codigoIncidencia) {
        this.codigoIncidencia = codigoIncidencia;
    }

    public ReclamoIncidencia(int codigoIncidencia, String motivoIncidencia, Bodeguero bodegueroEncargado) {
        this.codigoIncidencia = codigoIncidencia;
        this.motivoIncidencia = motivoIncidencia;
        this.bodegueroEncargado = bodegueroEncargado;
    }
    
    

//    public void agregarProducto(Producto producto) {
//        this.productos.add(producto);
//    }

    public ReclamoIncidencia(int codigoIncidencia, Producto producto, String motivoIncidencia, Usuario bodegueroEncargado) {
        this.codigoIncidencia = codigoIncidencia;
        this.producto = producto;
        this.motivoIncidencia = motivoIncidencia;
        this.bodegueroEncargado = bodegueroEncargado;
    }

}
