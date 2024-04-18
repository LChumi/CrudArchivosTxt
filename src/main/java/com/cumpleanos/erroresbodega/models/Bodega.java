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
