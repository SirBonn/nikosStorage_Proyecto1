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

@Getter
@Setter
@ToString
public class Catalogo {

    int codigo_tienda;
    List<Producto> productos = new ArrayList<>();

    public Catalogo(int codigo_tienda) {
        this.codigo_tienda = codigo_tienda;
    }

    public void agregarProducto(Producto producto) {
        this.productos.add(producto);
    }

    public Producto getProductos() {
        for (Producto producto : productos) {
            return producto;
        }
        return null;
    }

}
