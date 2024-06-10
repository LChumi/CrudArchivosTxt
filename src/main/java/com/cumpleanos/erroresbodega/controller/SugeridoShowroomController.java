/*
 * Copyright (c) 2024 Luis Chumi.
 * Este software está licenciado bajo la Licencia Pública General de GNU versión 3. Puedes encontrar una copia de la licencia en https://www.gnu.org/licenses/gpl-3.0.html.
 *
 * Para consultas o comentarios, puedes contactarme en "luischumi.9@gmail.com".
 * Me gustaría ser reconocido por mi trabajo y estar abierto a colaboraciones o enseñanzas sobre el programa.
 */

package com.cumpleanos.erroresbodega.controller;

import com.cumpleanos.erroresbodega.models.storage.ProductoShowroom;
import com.cumpleanos.erroresbodega.models.storage.SugeridoShowroom;
import com.cumpleanos.erroresbodega.services.SugeridoShowroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/sugerido/")
@CrossOrigin("*")
public class SugeridoShowroomController {

    @Autowired
    private SugeridoShowroomService showroomService;

    @PostMapping("guardar")
    public ResponseEntity<SugeridoShowroom> guardar(@RequestBody SugeridoShowroom sugerido){
        try {
            SugeridoShowroom nuevo = showroomService.guardarSugerido(sugerido);
            return ResponseEntity.ok(nuevo);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("buscar/{id}/")
    public ResponseEntity<SugeridoShowroom> buscar(@PathVariable Long id, @RequestParam String detalle){
        try {
            SugeridoShowroom sugeridoEncontrado = showroomService.getSugerido(id, detalle);
            return ResponseEntity.ok(sugeridoEncontrado);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("listar")
    public ResponseEntity<List<SugeridoShowroom>> listar(){
        try {
            List<SugeridoShowroom> sugeridos = showroomService.listar();
            return ResponseEntity.ok(sugeridos);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @PutMapping("agregarProducto/{id}/")
    public ResponseEntity<SugeridoShowroom> agregarProducto(@PathVariable Long id,
                                                                           @RequestParam String detalle,
                                                                           @RequestBody ProductoShowroom producto){
        try {
            SugeridoShowroom sugerido= showroomService.editarSugerido(id, detalle, producto);
            return ResponseEntity.ok(sugerido);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("eliminarProducto/{id}/")
    public ResponseEntity<SugeridoShowroom> eliminarProducto(@PathVariable Long id,
                                                                            @RequestParam String detalle,
                                                                            @RequestBody ProductoShowroom producto){
        try {
            SugeridoShowroom sugerido= showroomService.editarSUgeridoEliminar(id, detalle, producto);
            return ResponseEntity.ok(sugerido);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @PostMapping("exportar/excel")
    public ResponseEntity<InputStreamResource> exportarExcel(@RequestBody SugeridoShowroom sugerido) throws IOException{
        ByteArrayInputStream stream = showroomService.exportarExcel(sugerido);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename="+sugerido.getDetalle()+".xlsx");
        headers.add("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"); // Tipo de contenido correcto

        return ResponseEntity.ok()
                .headers(headers)
                .body(new InputStreamResource(stream));
    }

}
