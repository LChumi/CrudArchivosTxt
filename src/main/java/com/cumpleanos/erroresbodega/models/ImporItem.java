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
@Table(name = "IMPORITEM")
public class ImporItem {

    @Id
    @Column(name = "IIT_IMP_COMPROBA")
    private Long iitImpComproba;

    @Column(name = "IIT_PRODUCTO")
    private Long iitProducto;

    @Column(name = "IIT_EMPRESA")
    private Long iitEmpresa;

    @Column(name = "IIT_CANT_LIQUIDADA")
    private double iitCantLiquidada;

    @Column(name = "IIT_PRECIO")
    private double iitPrecio;

    @Column(name = "IIT_COSTO")
    private double iitCosto;


}
