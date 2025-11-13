/*
 * Copyright (c) 2024 Luis Chumi.
 * Este software está licenciado bajo la Licencia Pública General de GNU versión 3. Puedes encontrar una copia de la licencia en https://www.gnu.org/licenses/gpl-3.0.html.
 *
 * Para consultas o comentarios, puedes contactarme en "luischumi.9@gmail.com".
 * Me gustaría ser reconocido por mi trabajo y estar abierto a colaboraciones o enseñanzas sobre el programa.
 */

package com.cumpleanos.erroresbodega.services;

import com.cumpleanos.erroresbodega.models.ProductoView;

public interface ProductoService {
    ProductoView getByProIdOrProId1(String data);

    ProductoView porBarraOItem(Long bodega, String data);

    ProductoView porBarraEItem(Long bodega, String data, String item);

    String getMatches(Long bodega, String data, String item);

    String existInEmpresas(Long empresa, String barra , String item);
}
