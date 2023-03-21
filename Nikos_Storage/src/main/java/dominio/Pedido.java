/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio;

import java.sql.Date;
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
public class Pedido {

    private int codigoPedido;
    private Date fechaPedido = setNowDate();
    private double costoPedido;
    private String estadoPedido = "SOLICITADO";
    private Usuario dependiente;
    private ListadoProductos listadoProductos;
    private Tienda tienda;

    public Pedido() {
    }

    public Pedido(int codigoPedido) {
        this.codigoPedido = codigoPedido;
    }

    public Pedido(int codigoPedido, Usuario dependienteSolicitante, Tienda tienda) {
        this.codigoPedido = codigoPedido + 1;
        this.dependiente = dependienteSolicitante;
        this.tienda = tienda;
    }

    public Pedido(Usuario dependienteSolicitante) {
        this.dependiente = dependienteSolicitante;
    }

    public Pedido(int codigoPedido, String fechaPedido, double costoPedido, String estadoPedido, Usuario dependienteSolicitante, Tienda tienda) {
        this.codigoPedido = codigoPedido;
        this.fechaPedido = setLocalDate(fechaPedido);
        this.costoPedido = costoPedido;
        this.estadoPedido = estadoPedido;
        this.dependiente = dependienteSolicitante;
        this.tienda = tienda;
    }

    public Pedido(int codigoPedido, String fechaPedido, double costoPedido, String estadoPedido, Usuario dependienteSolicitante) {
        this.codigoPedido = codigoPedido;
        this.fechaPedido = setLocalDate(fechaPedido);
        this.costoPedido = costoPedido;
        this.estadoPedido = estadoPedido;
        this.dependiente = dependienteSolicitante;
    }

    public Date setNowDate() {
        Date fechaDate = null;
        try {

            LocalDate fechaLD = LocalDate.now();
            fechaDate = Date.valueOf(fechaLD);

        } catch (Exception e) {
            System.out.println("no se pudo parsear la fecha por " + e);
            e.printStackTrace(System.out);
        }
        return fechaDate;
    }

    public Date setLocalDate(String fecha) {
        Date fechaDate = null;
        try {

            LocalDate fechaLD = LocalDate.parse(fecha);
            fechaDate = Date.valueOf(fechaLD);

        } catch (Exception e) {
            System.out.println("no se pudo parsear la fecha" + fecha + " por " + e);
            e.printStackTrace(System.out);
        }

        return fechaDate;
    }

    public String getDatePedido() {

        LocalDate localDate = this.fechaPedido.toLocalDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String fechaStr = localDate.format(formatter);

        return fechaStr;
    }

    public int getDependienteSolicitante() {
        return dependiente.getCodigo();
    }

    public void crearLista(int codigo) {
        this.listadoProductos = new ListadoProductos(codigo);
    }

    public void crearLista() {
        this.listadoProductos = new ListadoProductos(this.codigoPedido);
    }

    public void agregarAlListado(Producto producto) {
        this.listadoProductos.agregarProducto(producto);
    }

}
