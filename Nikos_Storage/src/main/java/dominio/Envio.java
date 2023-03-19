/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Envio {

    private int codigoEnvio;
    private Date fechaEnvio;
    private Date fechaRecepcion;
    private String estado;
    private double totalEnvio; //total del envio
    private Pedido pedidoEnviado; //referencia al pedido enviado
    private Tienda tiendaDestino;
    private ListadoProductos listadoEnvio;

    public Envio() {
    }

    public Envio(int codigoEnvio) {
        this.codigoEnvio = codigoEnvio;
    }

    public Envio(int codigoEnvio, String fechaEnvio, String fechaRecepcion, String estado, double precioEnvio,
            Pedido productosEnviados, Tienda tiendaDestino) {
        this.codigoEnvio = codigoEnvio;
        this.fechaEnvio = setLocalDate(fechaEnvio);
        this.fechaRecepcion = setLocalDate(fechaRecepcion);
        this.estado = estado;
        this.totalEnvio = precioEnvio;
        this.pedidoEnviado = productosEnviados;
        this.tiendaDestino = tiendaDestino;
    }

    public Date setLocalDate(String fecha) {
        Date fechaDate = null;
        try {
            if (fecha != null) {

                LocalDate fechaLD = LocalDate.parse(fecha);
                fechaDate = Date.valueOf(fechaLD);
            }

        } catch (Exception e) {
            System.out.println("no se pudo parsear la fecha" + fecha + " por " + e);
            e.printStackTrace(System.out);
        }

        return fechaDate;
    }

    public String getDateEnvio() {

        LocalDate localDate = this.fechaEnvio.toLocalDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String fechaStr = localDate.format(formatter);

        return fechaStr;
    }

    public String getDateRecepcion() {
        String fechaStr = null;
        if (this.fechaRecepcion != null) {
            LocalDate localDate = this.fechaRecepcion.toLocalDate();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
             fechaStr = localDate.format(formatter);
        } 

        return fechaStr;
    }

    public int getTiendaDestino() {
        return tiendaDestino.getCodigo_tienda();
    }

    public void crearLista() {
        this.listadoEnvio = new ListadoProductos(this.codigoEnvio);
    }

    public void agregarAlListado(Producto producto) {
        this.listadoEnvio.agregarProducto(producto);
    }

}
