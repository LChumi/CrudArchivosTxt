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

import java.util.concurrent.atomic.AtomicLong;

@Setter
@Getter
public class ProductoShowroom {

    private static final AtomicLong ID_GENERATOR = new AtomicLong(0);

    private Long id;
    private String barra;
    private String item;
    private String descripcion;
    private String stockNc;
    private String stockZh;
    private String stockSh;
    private int cantidad;
    private String observacion;

    public void generarNuevoId(){
        this.id = ID_GENERATOR.incrementAndGet();
    }

}
