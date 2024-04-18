/*
 * Copyright (c) 2024 Luis Chumi.
 * Este software está licenciado bajo la Licencia Pública General de GNU versión 3. Puedes encontrar una copia de la licencia en https://www.gnu.org/licenses/gpl-3.0.html.
 *
 * Para consultas o comentarios, puedes contactarme en "luischumi.9@gmail.com".
 * Me gustaría ser reconocido por mi trabajo y estar abierto a colaboraciones o enseñanzas sobre el programa.
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

    @GetMapping("/consulta")
    public List<ProductoSugeridoDTO> get(@RequestParam String barra){
        try {
            List<ProductoSugeridoDTO> objects= dtoService.obtenerDatosBarra(barra);
            return objects;
        }catch (Exception e){
            return null;
        }
    }
}
