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

/**
 *
 * @author sirbon
 */
@ToString
@Getter
@Setter
public class Devolucion {

    private int codigoDevolucion;
    private LocalDate fechaDevolucion;
    private String estadoDevolucion;
    private double totalDevuelto;
    private Usuario encargado;
    private Envio envioDevuelto; //referencia codigo envio
    private ReclamoDevolucion reclamoDevolucion;
    
    public Devolucion() {
    }

    public Devolucion(int codigoDevolucion) {
        this.codigoDevolucion = codigoDevolucion;
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

    public Timestamp getTimestampDevolucion() {
        Timestamp timestamp = null;
        if (fechaDevolucion != null) {
            timestamp = Timestamp.valueOf(this.fechaDevolucion.atStartOfDay());
        }

        return timestamp;
    }

    public int getEncargado(){
        return encargado.getCodigo();
    }
 
    public void crearReclamo(){
        this.reclamoDevolucion = new ReclamoDevolucion(this.codigoDevolucion);
    }
    
    public void agregarProducto(Producto producto){
        reclamoDevolucion.agregarProducto(producto);
    }
    
}
