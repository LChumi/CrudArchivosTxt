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
@Table(name = "FAC_DESPEDIDOWEB_V")
public class DespachoPedido {

    @Id
    @Column(name = "DPW_ROWID")
    private String id;

    @Column(name = "DPW_USR_ID")
    private String usuario;

    @Column(name = "DPW_USR_NOMBRE")
    private String nombre;

    @Column(name = "DPW_COMPROBANTE")
    private String comprobante;

    @Column(name = "DPW_EMPRESA")
    private Long empresa;

    @Column(name = "DPW_CCO_CODIGO")
    private BigInteger codigoCco;

    @Column(name = "DPW_ESTADO")
    private Long estado;

    @Column(name = "DPW_CLIENTE")
    private String cliente;

    @Column(name = "DPW_PEDIDO_INTERNO")
    private Long pedido_interno;

}
