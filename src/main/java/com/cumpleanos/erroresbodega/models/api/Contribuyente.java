/*
 * Copyright (c) 2024 Luis Chumi.
 * Este software está licenciado bajo la Licencia Pública General de GNU versión 3. Puedes encontrar una copia de la licencia en https://www.gnu.org/licenses/gpl-3.0.html.
 *
 * Para consultas o comentarios, puedes contactarme en "luischumi.9@gmail.com".
 * Me gustaría ser reconocido por mi trabajo y estar abierto a colaboraciones o enseñanzas sobre el programa.
 */

package com.cumpleanos.erroresbodega.models.api;

import lombok.Data;

@Data
public class Contribuyente {
    private String identificacion;
    private String denominacion;
    private String tipo;
    private String clase;
    private String tipoIdentificacion;
    private String resolucion;
    private String nombreComercial;
    private String direccionMatriz;

    // Cambiar el tipo de fecha a Long o Date
    private Long fechaInformacion;

    private String mensaje;
    private String estado;
}
