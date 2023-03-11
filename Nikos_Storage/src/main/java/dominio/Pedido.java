/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
public class Pedido{

    private int codigoPedido;
    private LocalDate fechaPedido;
    private double costoPedido;
    private String estadoPedido;
    private Usuario dependienteSolicitante;
    private ListadoProductos listadoProductos;
    

    public Pedido() {
    }

    public Pedido(int codigoPedido) {
        this.codigoPedido = codigoPedido;
    }

    public Pedido(int codigoPedido, String fechaPedido, double costoPedido, String estadoPedido, Usuario dependienteSolicitante) {
        this.codigoPedido = codigoPedido;
        this.fechaPedido = setLocalDate(fechaPedido);
        this.costoPedido = costoPedido;
        this.estadoPedido = estadoPedido;
        this.dependienteSolicitante = dependienteSolicitante;
    }

    public LocalDate setLocalDate(String fecha) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fechaLD = LocalDate.parse(fecha, dateTimeFormatter);

        return fechaLD;
    }

    public Timestamp getTimestampPedido() {
        Timestamp timestamp = Timestamp.valueOf(this.fechaPedido.atTime(LocalTime.MIN));

        return timestamp;
    }

    public int getDependienteSolicitante() {
        return dependienteSolicitante.getCodigo();
    }
    
    public void crearLista(){
        this.listadoProductos = new ListadoProductos(this.codigoPedido);
    }
    
    public void agregarAlListado(Producto producto){
        this.listadoProductos.agregarProducto(producto);
    }

}
