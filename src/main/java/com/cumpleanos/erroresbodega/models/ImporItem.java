/*
 * Copyright (c) 2024 Luis Chumi.
 * Este software está licenciado bajo la Licencia Pública General de GNU versión 3. Puedes encontrar una copia de la licencia en https://www.gnu.org/licenses/gpl-3.0.html.
 *
 * Para consultas o comentarios, puedes contactarme en "luischumi.9@gmail.com".
 * Me gustaría ser reconocido por mi trabajo y estar abierto a colaboraciones o enseñanzas sobre el programa.
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
