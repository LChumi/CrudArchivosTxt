/*
 * Copyright (c) 2024.
 *  Este código es propiedad de Luis Chumi y está protegido por las leyes de derechos de autor.
 *  Se concede el permiso para usar, copiar, modificar y distribuir este software con la condición de que se incluya este aviso en todas las copias o partes sustanciales del software.
 *  Para obtener ayuda, soporte o permisos adicionales, contacta a Luis Chumi en luischumi.9@gmail.com.
 */

package com.cumpleanos.erroresbodega.models.storage;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
