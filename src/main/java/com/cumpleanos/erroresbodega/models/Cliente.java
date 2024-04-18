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
@Table(name = "cliente")
public class Cliente {

    @Id
    @Column(name = "CLI_CODIGO")
    private Long cliCodigo;

    @Column(name = "CLI_NOMBRE")
    private String cliNombre;

    @Column(name = "CLI_DIRECCION")
    private String cliDireccion;

    @Column(name = "CLI_RUC_CEDULA")
    private String cliCedula;

}
