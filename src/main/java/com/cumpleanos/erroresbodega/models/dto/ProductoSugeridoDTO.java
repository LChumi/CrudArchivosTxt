/*
 * Copyright (c) 2024 Luis Chumi.
 * Este software está licenciado bajo la Licencia Pública General de GNU versión 3. Puedes encontrar una copia de la licencia en https://www.gnu.org/licenses/gpl-3.0.html.
 *
 * Para consultas o comentarios, puedes contactarme en "luischumi.9@gmail.com".
 * Me gustaría ser reconocido por mi trabajo y estar abierto a colaboraciones o enseñanzas sobre el programa.
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
