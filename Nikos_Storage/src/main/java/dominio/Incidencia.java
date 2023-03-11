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
public class Incidencia {

    private int codigo;
    private LocalDate fechaIncidencia;
    private String estadoIncidencia;
    private Usuario encargado;
    private int envioDevuelto; //referencia codigo envio
    private ReclamoIncidencia reclamoIncidencia;
    private String solucion;
    
    public Incidencia() {
    }

    public Incidencia(int codigo) {
        this.codigo = codigo;
    }

    public Incidencia(int codigo, String fechaIncidencia, String estadoIncidencia, Usuario encargado) {
        this.codigo = codigo;
        this.fechaIncidencia = setLocalDate(fechaIncidencia);
        this.estadoIncidencia = estadoIncidencia;
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
        if (fechaIncidencia != null) {
            timestamp = Timestamp.valueOf(this.fechaIncidencia.atStartOfDay());
        }

        return timestamp;
    }

    public int getEncargado() {
        return encargado.getCodigo();
    }

    public void crearReclamo() {
        this.reclamoIncidencia = new ReclamoIncidencia(this.codigo);
    }

    public void agregarProducto(Producto producto) {
        reclamoIncidencia.agregarProducto(producto);
    }

}
