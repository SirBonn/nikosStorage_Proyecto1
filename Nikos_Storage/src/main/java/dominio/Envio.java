/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio;

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
    private LocalDate fechaEnvio;
    private LocalDate fechaRecepcion;
    private String estado;
    private double totalEnvio; //total del envio
    private int productosEnviados; //cantidad de productos enviados
    private Tienda tiendaDestino;
    private ListadoProductos listadoEnvio;

    public Envio() {
    }

    public Envio(int codigoEnvio) {
        this.codigoEnvio = codigoEnvio;
    }

    public Envio(int codigoEnvio, String fechaEnvio, String fechaRecepcion, String estado, double precioEnvio,
            int productosEnviados, Tienda tiendaDestino) {
        this.codigoEnvio = codigoEnvio;
        this.fechaEnvio = setLocalDate(fechaEnvio);
        this.fechaRecepcion = setLocalDate(fechaRecepcion);
        this.estado = estado;
        this.totalEnvio = precioEnvio;
        this.productosEnviados = productosEnviados;
        this.tiendaDestino = tiendaDestino;
    }

    public LocalDate setLocalDate(String fecha) {
        LocalDate fechaLD = null;
        try {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            fechaLD = LocalDate.parse(fecha, dateTimeFormatter);

        } catch (Exception e) {
            System.out.println("no se pudo parsear la fecha" + fecha + " por " + e);
        }

        return fechaLD;
    }

    public Timestamp getTimestampEnvio() {
        Timestamp timestamp = null;
        if (fechaEnvio != null) {
            timestamp = Timestamp.valueOf(this.fechaEnvio.atStartOfDay());

        }
        return timestamp;
    }

    public Timestamp getTimestampRecepcion() {
        Timestamp timestamp = null;
        if (fechaRecepcion != null) {
            timestamp = Timestamp.valueOf(this.fechaRecepcion.atStartOfDay());
        }

        return timestamp;
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
