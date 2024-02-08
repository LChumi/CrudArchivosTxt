/*
 * Copyright (c) 2023.
 *  Este código es propiedad de Luis Chumi y está protegido por las leyes de derechos de autor.
 *  Se concede el permiso para usar, copiar, modificar y distribuir este software con la condición de que se incluya este aviso en todas las copias o partes sustanciales del software.
 *  Para obtener ayuda, soporte o permisos adicionales, contacta a Luis Chumi en luischumi.9@gmail.com.
 */

package com.cumpleanos.erroresbodega.controller;

import com.cumpleanos.erroresbodega.models.storage.Observacion;
import com.cumpleanos.erroresbodega.models.storage.ObservacionCorrecion;
import com.cumpleanos.erroresbodega.services.ObservacionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/observacion")
@CrossOrigin("*")
public class ObservacionController {

    @Autowired
    private ObservacionService observacionService;

    @PostMapping("/guardar")
    public ResponseEntity<Observacion> guardar(@RequestBody Observacion observacion){
        try {
             Observacion observacion1 = observacionService.guardarObservacion(observacion);
             return ResponseEntity.ok(observacion1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/guardarLista")
    public ResponseEntity<List<Observacion>> guardarLista(@RequestBody List<Observacion> observaciones){
        try {
            List<Observacion> observacionLista=observacionService.guardarObservaciones(observaciones);
            return ResponseEntity.ok(observacionLista);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/buscar/{fecha}/{idUsuario}")
    public List<String> buscarporUsuarioFecha(@PathVariable String fecha,
                                              @PathVariable String idUsuario,
                                              @PathVariable String idProducto){
        try{
            return observacionService.getObservacionByUsuario(fecha,idUsuario,idProducto);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/buscar/{idProducto}")
    public List<String> buscarporProducto(@PathVariable String idProducto){
        try{
            return observacionService.getByProducto(idProducto);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Observacion>> Listar() {
        try {
            List<Observacion> listaObservaciones = observacionService.listarObservaciones();
            return ResponseEntity.ok(listaObservaciones);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/agregarCorrecion/")
    public Observacion agregarCorrecion(@RequestBody ObservacionCorrecion obj){
        try{
            return observacionService.editarObservacion(obj.getObservacion(), obj.getCorreccion());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/exportar/excel")
    public ResponseEntity<InputStreamResource> exportAllData() throws Exception {
        ByteArrayInputStream stream = observacionService.exportarExcel();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=observaciones.xlsx");
        headers.add("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"); // Tipo de contenido correcto

        return ResponseEntity.ok()
                .headers(headers)
                .body(new InputStreamResource(stream));
    }
}
