/*
 * Copyright (c) 2023.
 *  Este código es propiedad de Luis Chumi y está protegido por las leyes de derechos de autor.
 *  Se concede el permiso para usar, copiar, modificar y distribuir este software con la condición de que se incluya este aviso en todas las copias o partes sustanciales del software.
 *  Para obtener ayuda, soporte o permisos adicionales, contacta a Luis Chumi en luischumi.9@gmail.com.
 */

package com.cumpleanos.erroresbodega.controller;

import com.cumpleanos.erroresbodega.models.Observacion;
import com.cumpleanos.erroresbodega.models.ObservacionCorrecion;
import com.cumpleanos.erroresbodega.services.ObservacionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            observacion.calcularPrecioTotal();
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
    public ResponseEntity<List<Observacion>> Listar(){
        try{
            List<String> listaArchivos=observacionService.listarObservaciones();
            List<Observacion> listaObservaciones =new ArrayList<>();

            ObjectMapper objectMapper = new ObjectMapper();

            for (String nombreArchivo:listaArchivos){
                List<String> contenidoArchivo= observacionService.obtenerContenidoObservacion(nombreArchivo);
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
            return observacionService.editarObservacion(obj.getObservacion(), obj.getCorreccion());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
