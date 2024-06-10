/*
 * Copyright (c) 2024 Luis Chumi.
 * Este software está licenciado bajo la Licencia Pública General de GNU versión 3. Puedes encontrar una copia de la licencia en https://www.gnu.org/licenses/gpl-3.0.html.
 *
 * Para consultas o comentarios, puedes contactarme en "luischumi.9@gmail.com".
 * Me gustaría ser reconocido por mi trabajo y estar abierto a colaboraciones o enseñanzas sobre el programa.
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
