/*
 * Copyright (c) 2024.
 *  Este código es propiedad de Luis Chumi y está protegido por las leyes de derechos de autor.
 *  Se concede el permiso para usar, copiar, modificar y distribuir este software con la condición de que se incluya este aviso en todas las copias o partes sustanciales del software.
 *  Para obtener ayuda, soporte o permisos adicionales, contacta a Luis Chumi en luischumi.9@gmail.com.
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
