package com.cumpleanos.erroresbodega.models;

import lombok.Data;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Immutable
@Subselect("SELECT V.SECUENCIA,V.BOD_NOMBRE,V.PRO_ID1,V.PRO_ID,V.PRO_NOMBRE,V.UNIDAD,V.BULTO,V.PVP,V.CXB,V.STOCK_REAL,V.BOD_CODIGO FROM INV_PRODINFGEN_WEB_V V")
@Data
public class ProductoView {

    @Id
    @Column(name = "SECUENCIA")
    private String secuencia;

    @Column(name = "PRO_ID")
    private String pro_id;

    @Column(name = "PRO_ID1")
    private String pro_id1;

    @Column(name = "CXB")
    private Integer cxb;

    @Column(name = "PRO_NOMBRE")
    private String pro_nombre;

    @Column(name = "BOD_NOMBRE")
    private String bod_nombre;

    @Column(name = "PVP")
    private Double pvp;

    @Column(name ="UNIDAD")
    private String unidad;

    @Column(name = "BULTO")
    private String bulto;

    @Column(name ="STOCK_REAL")
    private Integer stock_real;

    @Column(name = "BOD_CODIGO")
    private Long bod_codigo;
}

