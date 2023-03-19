/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio;

import java.util.ArrayList;
import java.util.List;
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
public class ListadoProductos {

    private int codigoListado; //referencia al codigo de envio
    private List<Producto> listadoProductos= new ArrayList<>();

    public ListadoProductos() {
    }

    public void agregarProducto(Producto producto) {
        this.listadoProductos.add(producto);
    }
    
    public ListadoProductos(int codigoPedido) {
        this.codigoListado = codigoPedido;
    }
//
//    public void agregarProducto(Producto producto){
//        this.listadoProductos.add(producto);
//    }
    
}
