package dominio;

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
public class Tienda {

    private int codigo_tienda;
    private String nombre = "";
    private String direccion;
    private String tipo;
    private Catalogo catalogo; //refiere a la tabla que almacena muchos productos en muchas tiendas
    private Pedido pedido;

    public Tienda() {
    }

    public Tienda(int codigo_tienda) {
        this.codigo_tienda = codigo_tienda;
    }

    public Tienda(int codigo_tienda, String direccion, String tipo) {
        this.codigo_tienda = codigo_tienda;
        this.direccion = direccion;
        this.tipo = tipo;
    }

    public Tienda(int codigo_tienda, String nombre, String direccion, String esSupervisada) {
        this.codigo_tienda = codigo_tienda;
        this.nombre = nombre;
        this.direccion = direccion;
        this.tipo = esSupervisada;
    }

    public Tienda(String nombre, String direccion, String tipo) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.tipo = tipo;
    }

    public void crearCatalogo(){
        this.catalogo = new Catalogo(this.codigo_tienda);
    }
    
    public void agregarAlCatalogo(Producto producto) {
        catalogo.agregarProducto(producto);

    }

}
