/*
 * Copyright (c) 2026 Luis Chumi.
 * Este software está licenciado bajo la Licencia Pública General de GNU versión 3. Puedes encontrar una copia de la licencia en https://www.gnu.org/licenses/gpl-3.0.html.
 *
 * Para consultas o comentarios, puedes contactarme en "luischumi.9@gmail.com".
 * Me gustaría ser reconocido por mi trabajo y estar abierto a colaboraciones o enseñanzas sobre el programa.
 */

package com.cumpleanos.erroresbodega.models.dto;

public record ConfiteriaRepor(
        String ultVenta,
        String ultComp,
        Long ultCantCom,
        Double cantVenta,
        Long stockIni,
        String cliNombre,
        String barra,
        String item,
        String proNombre,
        Long stockDisp,
        Long stockReal,
        Double pvp
) {
}