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
