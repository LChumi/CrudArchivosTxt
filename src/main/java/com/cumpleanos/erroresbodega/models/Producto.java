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
@Table(name = "PRODUCTO")
public class Producto {

    @Id
    @Column(name = "PRO_CODIGO")
    private Long proCodigo;

    @Column(name = "PRO_ID")
    private String proId;

    @Column(name = "PRO_NOMBRE")
    private String proNombre;

    @Column(name = "PRO_ID1")
    private String proId1;

    @Column(name = "PRO_EMPRESA")
    private Long proEmpresa;

    @Column(name = "PRO_PROVEEDOR")
    private Long proProveedor;

}
