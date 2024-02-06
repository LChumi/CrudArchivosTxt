/*
 * Copyright (c) 2024.
 *  Este código es propiedad de Luis Chumi y está protegido por las leyes de derechos de autor.
 *  Se concede el permiso para usar, copiar, modificar y distribuir este software con la condición de que se incluya este aviso en todas las copias o partes sustanciales del software.
 *  Para obtener ayuda, soporte o permisos adicionales, contacta a Luis Chumi en luischumi.9@gmail.com.
 */

package com.cumpleanos.erroresbodega.controller;

import com.cumpleanos.erroresbodega.models.storage.Observacion;
import com.cumpleanos.erroresbodega.models.storage.ObservacionCorrecion;
import com.cumpleanos.erroresbodega.services.ObservacionNarancayService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/observacion_narancay")
@CrossOrigin("*")
public class ObservacionNarancayController {

    @Autowired
    private ObservacionNarancayService narancayService;

    @PostMapping("/guardar")
    public ResponseEntity<Observacion> guardar(@RequestBody Observacion observacion){
        try {
            Observacion observacion1 = narancayService.guardarObservacion(observacion);
            return ResponseEntity.ok(observacion1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Observacion>> Listar(){
        try{
            List<String> listaArchivos=narancayService.listarObservaciones();
            List<Observacion> listaObservaciones =new ArrayList<>();

            ObjectMapper objectMapper = new ObjectMapper();

            for (String nombreArchivo:listaArchivos){
                List<String> contenidoArchivo= narancayService.obtenerContenidoObservacion(nombreArchivo);
                for (String linea: contenidoArchivo){
                    Observacion observacion = objectMapper.readValue(linea, Observacion.class);
                    listaObservaciones.add(observacion);
                }
            }

            Collections.sort(listaObservaciones);

            return ResponseEntity.ok(listaObservaciones);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/agregarCorrecion/")
    public Observacion agregarCorrecion(@RequestBody ObservacionCorrecion obj){
        try{
            return narancayService.editarObservacion(obj.getObservacion(), obj.getCorreccion());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
