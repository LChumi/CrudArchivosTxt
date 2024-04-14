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

@Entity
@Data
@Table(name ="USUARIO_WEB")
public class Usuario {

    @Id
    @Column(name = "USR_CODIGO")
    private Long usr_codigo;

    @Column(name = "USR_ID")
    private String usr_id;

    @Column(name = "USR_NOMBRE")
    private String usr_nombre;

    @Column(name = "USR_INACTIVO")
    private Integer usr_inactivo;

    @Column(name = "USR_CLAVE")
    private String usr_clave;

    @Column(name = "USR_EMPRESA_DEF")
    private Integer usr_empresa_def;

    @Column(name = "USR_ROL_WEB")
    private String usr_rol_web;
}
