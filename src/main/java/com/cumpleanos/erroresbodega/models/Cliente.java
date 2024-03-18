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
