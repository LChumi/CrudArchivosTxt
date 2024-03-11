/*
 * Copyright (c) 2024.
 *  Este código es propiedad de Luis Chumi y está protegido por las leyes de derechos de autor.
 *  Se concede el permiso para usar, copiar, modificar y distribuir este software con la condición de que se incluya este aviso en todas las copias o partes sustanciales del software.
 *  Para obtener ayuda, soporte o permisos adicionales, contacta a Luis Chumi en luischumi.9@gmail.com.
 */

package com.cumpleanos.erroresbodega.controller;

import com.cumpleanos.erroresbodega.models.storage.Calificacion;
import com.cumpleanos.erroresbodega.services.CalificacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("calificacion")
@RequiredArgsConstructor
public class CalificacionController {

    private final CalificacionService calificacionService;

    @PostMapping("/guardar")
    public ResponseEntity<Calificacion> guardar(@RequestBody Calificacion calificacion){
        try {
            Calificacion c = calificacionService.guardarCalificacion(calificacion);
            return ResponseEntity.ok(c);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Calificacion>> listar(){
        try {
            List<Calificacion> calificaciones = calificacionService.listarCalificaciones();
            return ResponseEntity.ok(calificaciones);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
