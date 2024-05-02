/*
 * Copyright (c) 2024 Luis Chumi.
 * Este software está licenciado bajo la Licencia Pública General de GNU versión 3. Puedes encontrar una copia de la licencia en https://www.gnu.org/licenses/gpl-3.0.html.
 *
 * Para consultas o comentarios, puedes contactarme en "luischumi.9@gmail.com".
 * Me gustaría ser reconocido por mi trabajo y estar abierto a colaboraciones o enseñanzas sobre el programa.
 */

package com.cumpleanos.erroresbodega.models.storage;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicLong;

@Getter
@Setter
public class Observacion implements Serializable, Comparable<Observacion> {

    private static final AtomicLong idGenerator = new AtomicLong(0);

    private Long id;
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
    private String diferencia;
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

    public Long generarNuevoId(){
        return this.id=idGenerator.incrementAndGet();
    }
}
