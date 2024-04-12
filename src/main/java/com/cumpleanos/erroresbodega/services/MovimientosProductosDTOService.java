/*
 * Copyright (c) 2024.
 *  Este código es propiedad de Luis Chumi y está protegido por las leyes de derechos de autor.
 *  Se concede el permiso para usar, copiar, modificar y distribuir este software con la condición de que se incluya este aviso en todas las copias o partes sustanciales del software.
 *  Para obtener ayuda, soporte o permisos adicionales, contacta a Luis Chumi en luischumi.9@gmail.com.
 */

package com.cumpleanos.erroresbodega.services;

import com.cumpleanos.erroresbodega.models.storage.MovimientosProductosDTO;
import com.cumpleanos.erroresbodega.models.storage.ProductoDTO;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

public interface MovimientosProductosDTOService {

    List<MovimientosProductosDTO> listar() throws IOException;
    MovimientosProductosDTO guardar(MovimientosProductosDTO movimientosProductosDTO) throws IOException;
    ByteArrayInputStream exportarExcel() throws IOException;
    MovimientosProductosDTO editarMovimiento(MovimientosProductosDTO movimientosProductosDTO,ProductoDTO productoDTO) throws IOException;
}
