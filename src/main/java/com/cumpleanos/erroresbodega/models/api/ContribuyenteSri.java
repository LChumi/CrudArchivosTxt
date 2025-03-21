/*
 * Copyright (c) 2024 Luis Chumi.
 * Este software está licenciado bajo la Licencia Pública General de GNU versión 3. Puedes encontrar una copia de la licencia en https://www.gnu.org/licenses/gpl-3.0.html.
 *
 * Para consultas o comentarios, puedes contactarme en "luischumi.9@gmail.com".
 * Me gustaría ser reconocido por mi trabajo y estar abierto a colaboraciones o enseñanzas sobre el programa.
 */

package com.cumpleanos.erroresbodega.models.api;

import lombok.Data;

import java.util.Map;

@Data
public class ContribuyenteSri {
    private Contribuyente contribuyente;
    private Map<String, Object> deuda;
    private Map<String, Object> impugnacion;
    private Map<String, Object> remision;
}
