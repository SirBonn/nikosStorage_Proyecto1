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
    private String nombre;
    private String direccion;
    private boolean esSupervisada;
    private int codigo_supervisor;
    private int codigo_bodeguero;

    public Tienda() {
    }

    public Tienda(int codigo_tienda) {
        this.codigo_tienda = codigo_tienda;
    }

    //constructores tiendas no supervisadas
    public Tienda(int codigo_tienda, String nombre, String direccion, boolean esSupervisada, int codigo_bodeguero) {
        this.codigo_tienda = codigo_tienda;
        this.nombre = nombre;
        this.direccion = direccion;
        this.esSupervisada = esSupervisada;
        this.codigo_bodeguero = codigo_bodeguero;
    }

    public Tienda(String nombre, String direccion, boolean esSupervisada, int codigo_bodeguero) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.esSupervisada = esSupervisada;
        this.codigo_bodeguero = codigo_bodeguero;
    }

    // constructores tiendas supervisadas
    public Tienda(int codigo_tienda, String nombre, String direccion, boolean esSupervisada, int codigo_supervisor, int codigo_bodeguero) {
        this.codigo_tienda = codigo_tienda;
        this.nombre = nombre;
        this.direccion = direccion;
        this.esSupervisada = esSupervisada;
        this.codigo_supervisor = codigo_supervisor;
        this.codigo_bodeguero = codigo_bodeguero;
    }

    public Tienda(String nombre, String direccion, boolean esSupervisada, int codigo_supervisor, int codigo_bodeguero) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.esSupervisada = esSupervisada;
        this.codigo_supervisor = codigo_supervisor;
        this.codigo_bodeguero = codigo_bodeguero;
    }
    
    
}
