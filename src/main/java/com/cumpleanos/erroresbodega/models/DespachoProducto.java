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
import java.math.BigInteger;

@Entity
@Data
@Table(name = "FAC_DESPROD_WEB_V")
public class DespachoProducto {

    @Id
    @Column(name = "DPR_ROWID")
    private String id;

    @Column(name = "DPR_COMPROBANTE")
    private String comprobante;

    @Column(name = "DPR_EMPRESA")
    private Long empresa;

    @Column(name = "DPR_CCO_CODIGO")
    private BigInteger ccoCodigo;

    @Column(name = "DPR_TIPODOC")
    private int tipoDoc;

    @Column(name = "DPR_PRO_ID")
    private String proId;

    @Column(name = "DPR_PRO_ID1")
    private String proId1;

    @Column(name = "DPR_PRO_NOMBRE")
    private String proNombre;

    @Column(name = "DPR_PRO_PRECIO2")
    private double proPrecio;

    @Column(name = "DPR_CDIGITADA")
    private int cantidad;

    @Column(name = "DPR_OBSERVACION")
    private String observacion;

}
