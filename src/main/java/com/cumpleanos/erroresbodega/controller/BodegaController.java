/*
 * Copyright (c) 2024.
 *  Este código es propiedad de Luis Chumi y está protegido por las leyes de derechos de autor.
 *  Se concede el permiso para usar, copiar, modificar y distribuir este software con la condición de que se incluya este aviso en todas las copias o partes sustanciales del software.
 *  Para obtener ayuda, soporte o permisos adicionales, contacta a Luis Chumi en luischumi.9@gmail.com.
 */

package com.cumpleanos.erroresbodega.controller;

import com.cumpleanos.erroresbodega.models.Bodega;
import com.cumpleanos.erroresbodega.services.BodegaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/bodegas")
@CrossOrigin("*")
public class BodegaController {

    @Autowired
    private BodegaService bodegaService;

    private final static Logger console= LoggerFactory.getLogger(BodegaController.class);

    @GetMapping("/listaBodegas/{usuario}/{empresa}")
    public ResponseEntity<List<Bodega>> listar(@PathVariable Long usuario,
                                               @PathVariable Long empresa){
        try {
            List<Bodega> bodegas = bodegaService.listarBodegas(usuario, empresa);

            if(bodegas.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(bodegas,HttpStatus.OK);
        }catch (Exception e){
            console.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
