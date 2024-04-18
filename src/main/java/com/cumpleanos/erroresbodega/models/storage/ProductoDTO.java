/*
 * Copyright (c) 2024 Luis Chumi.
 * Este software está licenciado bajo la Licencia Pública General de GNU versión 3. Puedes encontrar una copia de la licencia en https://www.gnu.org/licenses/gpl-3.0.html.
 *
 * Para consultas o comentarios, puedes contactarme en "luischumi.9@gmail.com".
 * Me gustaría ser reconocido por mi trabajo y estar abierto a colaboraciones o enseñanzas sobre el programa.
 */

package com.cumpleanos.erroresbodega.models.storage;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.atomic.AtomicLong;

@Setter
@Getter
public class ProductoDTO {

    private static final AtomicLong ID_GENERATOR = new AtomicLong(0);

    @Setter(AccessLevel.NONE)
    private Long id;
    private String barra;
    private String detalle;
    private String item;
    private int cantidad;
    private String observacion;

    public Long generarNuevoId(){
        return this.id=ID_GENERATOR.incrementAndGet();
    }

}
