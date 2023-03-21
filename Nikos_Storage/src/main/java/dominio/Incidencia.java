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
public class Incidencia {

    private int codigo;
    private Date fechaIncidencia;
    private String estadoIncidencia;
    private Usuario encargado;
    private Envio envioDevuelto; //referencia codigo envio
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

    public Incidencia(int codigo, String fechaIncidencia, String estadoIncidencia, Usuario encargado, Envio envioDevuelto) {
        this.codigo = codigo;
        this.fechaIncidencia = setLocalDate(fechaIncidencia);
        this.estadoIncidencia = estadoIncidencia;
        this.encargado = encargado;
        this.envioDevuelto = envioDevuelto;
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

    public String getDateIncidencia() {

        LocalDate localDate = this.fechaIncidencia.toLocalDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String fechaStr = localDate.format(formatter);

        return fechaStr;
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

    public int getEncargado() {
        return encargado.getCodigo();
    }

    public void crearReclamo() {
        this.reclamoIncidencia = new ReclamoIncidencia(this.codigo);
    }

    public void agregarProducto(Producto producto) {
        reclamoIncidencia.setProducto(producto);
    }

}
