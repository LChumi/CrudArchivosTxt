/*
 * Copyright (c) 2024 Luis Chumi.
 * Este software está licenciado bajo la Licencia Pública General de GNU versión 3.
 * Puedes encontrar una copia de la licencia en https://www.gnu.org/licenses/gpl-3.0.html.
 *
 * Para consultas o comentarios, puedes contactarme en luischumi.9@gmail.com
 */

package com.cumpleanos.erroresbodega.utils;

import com.cumpleanos.erroresbodega.models.storage.Observacion;
import org.apache.poi.ss.usermodel.Row;

@FunctionalInterface
public interface ExcelFiller {
    void fillRow(Row row, Observacion observacion);
}
