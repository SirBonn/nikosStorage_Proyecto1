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

    private int codigoTienda;
    private List<Producto> productos = new ArrayList<>();

    public Catalogo(int codigo_tienda) {
        this.codigoTienda = codigo_tienda;
    }

    public void agregarProducto(Producto producto) {
        this.productos.add(producto);
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    
}
