/*
 * Copyright (c) 2024.
 *  Este código es propiedad de Luis Chumi y está protegido por las leyes de derechos de autor.
 *  Se concede el permiso para usar, copiar, modificar y distribuir este software con la condición de que se incluya este aviso en todas las copias o partes sustanciales del software.
 *  Para obtener ayuda, soporte o permisos adicionales, contacta a Luis Chumi en luischumi.9@gmail.com.
 */

package com.cumpleanos.erroresbodega.controller;

import com.cumpleanos.erroresbodega.models.dto.ProductoSugeridoDTO;
import com.cumpleanos.erroresbodega.services.ProductoDTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productoDto")
@CrossOrigin("*")
public class ProductoDtoController {

    @Autowired
    ProductoDTOService dtoService;

    @GetMapping("consulta")
    public List<ProductoSugeridoDTO> get(@RequestParam String barra){
        try {
            List<ProductoSugeridoDTO> objects= dtoService.obtenerDatosBarra(barra);
            return objects;
        }catch (Exception e){
            return null;
        }
    }
}
