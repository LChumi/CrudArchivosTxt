/*
 * Copyright (c) 2024.
 *  Este código es propiedad de Luis Chumi y está protegido por las leyes de derechos de autor.
 *  Se concede el permiso para usar, copiar, modificar y distribuir este software con la condición de que se incluya este aviso en todas las copias o partes sustanciales del software.
 *  Para obtener ayuda, soporte o permisos adicionales, contacta a Luis Chumi en luischumi.9@gmail.com.
 */

package com.cumpleanos.erroresbodega.models;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "BODEGA_WEB_V")
public class Bodega {

    @Id
    @Column(name = "BOD_CODIGO")
    private Long bod_codigo;

    @Column(name = "BOD_USUARIO")
    private Long bod_usuario;

    @Column(name = "BOD_EMPRESA")
    private Long bod_empresa;

    @Column(name = "BOD_NOMBRE")
    private String bod_nombre;

    @Column(name = "BOD_ID")
    private String bod_id;


}
