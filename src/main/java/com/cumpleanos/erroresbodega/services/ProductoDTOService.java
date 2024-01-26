/*
 * Copyright (c) 2024.
 *  Este código es propiedad de Luis Chumi y está protegido por las leyes de derechos de autor.
 *  Se concede el permiso para usar, copiar, modificar y distribuir este software con la condición de que se incluya este aviso en todas las copias o partes sustanciales del software.
 *  Para obtener ayuda, soporte o permisos adicionales, contacta a Luis Chumi en luischumi.9@gmail.com.
 */

package com.cumpleanos.erroresbodega.services;

import com.cumpleanos.erroresbodega.models.dto.ProductoSugeridoDTO;
import com.cumpleanos.erroresbodega.repository.ProductoConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductoDTOService {

    @Autowired
    private ProductoConsultaRepository consultaRepository;

    public List<ProductoSugeridoDTO> obtenerDatosBarra(String barra){
        return consultaRepository.obtenerDetallesProducto(barra);
    }


}
