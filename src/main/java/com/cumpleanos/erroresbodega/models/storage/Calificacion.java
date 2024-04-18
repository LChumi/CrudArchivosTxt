/*
 * Copyright (c) 2024 Luis Chumi.
 * Este software está licenciado bajo la Licencia Pública General de GNU versión 3. Puedes encontrar una copia de la licencia en https://www.gnu.org/licenses/gpl-3.0.html.
 *
 * Para consultas o comentarios, puedes contactarme en "luischumi.9@gmail.com".
 * Me gustaría ser reconocido por mi trabajo y estar abierto a colaboraciones o enseñanzas sobre el programa.
 */

package com.cumpleanos.erroresbodega.models.storage;

import com.cumpleanos.erroresbodega.models.enums.CalificacionEnum;
import lombok.AccessLevel;
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
        LocalDate fechaActual = LocalDate.parse(this.fecha, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        LocalDate fechaCalifi = LocalDate.parse(calificacion.getFecha(),DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        return fechaCalifi.compareTo(fechaActual);
    }

    public Long generarNuevoId() {
        return this.id=idGenerator.incrementAndGet();
    }
}
