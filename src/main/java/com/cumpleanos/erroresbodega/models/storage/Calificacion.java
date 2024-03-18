/*
 * Copyright (c) 2024.
 *  Este código es propiedad de Luis Chumi y está protegido por las leyes de derechos de autor.
 *  Se concede el permiso para usar, copiar, modificar y distribuir este software con la condición de que se incluya este aviso en todas las copias o partes sustanciales del software.
 *  Para obtener ayuda, soporte o permisos adicionales, contacta a Luis Chumi en luischumi.9@gmail.com.
 */

package com.cumpleanos.erroresbodega.models.storage;

import com.cumpleanos.erroresbodega.models.CalificacionEnum;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicLong;

@Setter
@Getter
public class Calificacion implements Serializable,Comparable<Calificacion> {

    private static final AtomicLong idGenerator = new AtomicLong(0);

    @Setter(AccessLevel.NONE)
    private Long id;
    private String fecha;
    private String cliente;
    private String empleado;
    private String observacion;
    @Enumerated(EnumType.STRING)
    private CalificacionEnum calificacionEnum;
    private boolean aceptaPoliticas;

    @Override
    public int compareTo(Calificacion calificacion) {
        LocalDate fechaActual = LocalDate.parse(this.fecha, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalDate fechaCalifi = LocalDate.parse(calificacion.getFecha(),DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        return fechaCalifi.compareTo(fechaActual);
    }

    public Long generarNuevoId() {
        return this.id=idGenerator.incrementAndGet();
    }
}
