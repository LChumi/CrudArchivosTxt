/*
 * Copyright (c) 2024.
 *  Este código es propiedad de Luis Chumi y está protegido por las leyes de derechos de autor.
 *  Se concede el permiso para usar, copiar, modificar y distribuir este software con la condición de que se incluya este aviso en todas las copias o partes sustanciales del software.
 *  Para obtener ayuda, soporte o permisos adicionales, contacta a Luis Chumi en luischumi.9@gmail.com.
 */

package com.cumpleanos.erroresbodega.models.dto;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class ProductoSugeridoDTO {

    @Id
    private String proId;
    private String proId1;
    private String proNombre;
    private double iitCantLiquidada;
    private double iitPrecio;
    private double iitCosto;
    private String ccoFecha;
    private String cliNombre;
    private String cliDireccion;
    private int cantPedida;
    private String solicitante;

    public ProductoSugeridoDTO(String proId, String proId1, String proNombre, double iitCantLiquidada, double iitPrecio, double iitCosto, String ccoFecha, String cliNombre,String cliDireccion) {
        this.proId = proId;
        this.proId1 = proId1;
        this.proNombre = proNombre;
        this.iitCantLiquidada = iitCantLiquidada;
        this.iitPrecio = iitPrecio;
        this.iitCosto = iitCosto;
        this.ccoFecha = ccoFecha;
        this.cliNombre = cliNombre;
        this.cliDireccion=cliDireccion;
    }

    public ProductoSugeridoDTO() {

    }
}
