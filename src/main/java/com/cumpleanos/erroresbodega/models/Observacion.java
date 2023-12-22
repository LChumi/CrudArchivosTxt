package com.cumpleanos.erroresbodega.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Getter
@Setter
public class Observacion implements Serializable, Comparable<Observacion> {

    private String fecha;
    private String item;
    private String descripcion;
    private String bulto;
    private String unidad;
    private String cxb;
    private int stock;
    private double precio;
    private double precioTotal;
    private String usuario;
    private String detalle;
    private Correccion correccion;

    public void calcularPrecioTotal(){
        precioTotal=precio*stock;
    }

    @Override
    public int compareTo(Observacion observacion){
        LocalDate fechaActual= LocalDate.parse(this.fecha, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalDate fechaOb=LocalDate.parse(observacion.getFecha(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        return fechaOb.compareTo(fechaActual);
    }
}
