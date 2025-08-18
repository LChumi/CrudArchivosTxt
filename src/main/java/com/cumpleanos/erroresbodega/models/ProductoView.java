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
@Table(name = "INV_PRODINFGEN_WEB_V")
public class ProductoView {

    @Id
    @Column(name = "SECUENCIA")
    private String secuencia;

    @Column(name = "PRO_ID")
    private String proId;

    @Column(name = "PRO_ID1")
    private String proId1;

    @Column(name = "CXB")
    private Integer cxb;

    @Column(name = "PRO_NOMBRE")
    private String nombre;

    @Column(name = "BOD_NOMBRE")
    private String bodega;

    @Column(name = "PVP")
    private Double pvp;

    @Column(name ="UNIDAD")
    private String unidad;

    @Column(name = "BULTO")
    private String bulto;

    @Column(name ="STOCK_REAL")
    private Integer stockReal;

    @Column(name = "STOCK_DISP")
    private Integer stockDisponible;

    @Column(name = "BOD_CODIGO")
    private Long bodCodigo;

    @Column(name = "PRO_EMPRESA")
    private Long proEmpresa;

    @Column(name = "PRO_CODIGO")
    private Long proCodigo;
}

