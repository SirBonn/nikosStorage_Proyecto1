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

/**
 *
 * @author sirbon
 */
@ToString
@Getter
@Setter
public class Devolucion {

    private int codigoDevolucion;
    private Date fechaDevolucion;
    private String estadoDevolucion;
    private double totalDevuelto;
    private Usuario encargado;
    private Envio envioDevuelto; //referencia codigo envio
    private ReclamoDevolucion reclamoDevolucion;

    public Devolucion() {
    }

    public Devolucion(int codigoDevolucion) {
        this.codigoDevolucion = codigoDevolucion+1;
    }

    public Devolucion(int codigoDevolucion, String fechaDevolucion, String estadoDevolucion, double totalDevuelto, Envio envioDevuelto, Usuario encargado) {
        this.codigoDevolucion = codigoDevolucion;
        this.fechaDevolucion = setLocalDate(fechaDevolucion);
        this.estadoDevolucion = estadoDevolucion;
        this.totalDevuelto = totalDevuelto;
        this.envioDevuelto = envioDevuelto;
        this.encargado = encargado;
    }

    public Devolucion(int codigoDevolucion, String fechaDevolucion, String estadoDevolucion, double totalDevuelto, Usuario encargado) {
        this.codigoDevolucion = codigoDevolucion;
        this.fechaDevolucion = setLocalDate(fechaDevolucion);
        this.estadoDevolucion = estadoDevolucion;
        this.totalDevuelto = totalDevuelto;
        this.encargado = encargado;
    }

    public Devolucion(Date fechaDevolucion, String estadoDevolucion, double totalDevuelto, Usuario encargado) {
        this.fechaDevolucion = fechaDevolucion;
        this.estadoDevolucion = estadoDevolucion;
        this.totalDevuelto = totalDevuelto;
        this.encargado = encargado;
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

    public String getTimestampDevolucion() {
         LocalDate localDate = this.fechaDevolucion.toLocalDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String fechaStr = localDate.format(formatter);

        return fechaStr;
    }

    public int getEncargado() {
        return encargado.getCodigo();
    }

    public void crearReclamo() {
        this.reclamoDevolucion = new ReclamoDevolucion(this.codigoDevolucion);
    }

    public void agregarProducto(Producto producto) {
        reclamoDevolucion.setProductoDevuelto(producto);
    }

}
