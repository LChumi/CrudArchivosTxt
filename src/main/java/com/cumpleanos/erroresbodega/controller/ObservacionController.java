/*
 * Copyright (c) 2023-2024 Luis Chumi.
 * Este software está licenciado bajo la Licencia Pública General de GNU versión 3. Puedes encontrar una copia de la licencia en https://www.gnu.org/licenses/gpl-3.0.html.
 *
 * Para consultas o comentarios, puedes contactarme en "luischumi.9@gmail.com".
 * Me gustaría ser reconocido por mi trabajo y estar abierto a colaboraciones o enseñanzas sobre el programa.
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

    /**
     * Crud para la bodega de Zhucay
     */
    @PostMapping("/guardar/zhucay")
    public ResponseEntity<Observacion> guardar(@RequestBody Observacion observacion){
        try {
            Observacion observacion1 = observacionService.guardarObservacionZhucay(observacion);
            return ResponseEntity.ok(observacion1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/listarZhucay")
    public ResponseEntity<List<Observacion>> Listar() {
        try {
            List<Observacion> listaObservaciones = observacionService.listarZhucay();
            return ResponseEntity.ok(listaObservaciones);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @PutMapping("/agregarCorrecionZhucay/")
    public Observacion agregarCorrecion(@RequestBody ObservacionCorrecion obj){
        try{
            return observacionService.editarObservacionZhucay(obj.getObservacion(), obj.getCorreccion());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/excel/zhucay/")
    public ResponseEntity<InputStreamResource> excelZhucay() throws Exception {
        ByteArrayInputStream stream = observacionService.exportarExcelZhucay();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=observaciones.xlsx");
        headers.add("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"); // Tipo de contenido correcto

        return ResponseEntity.ok()
                .headers(headers)
                .body(new InputStreamResource(stream));
    }

    /**
     * Crud para la bodega de Narancay
     */
    @PostMapping("/guardar/narancay")
    public ResponseEntity<Observacion> guardarNarancay(@RequestBody Observacion observacion){
        try {
            Observacion observacion1 = observacionService.guardarObservacionNarancay(observacion);
            return ResponseEntity.ok(observacion1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/listarNarancay")
    public ResponseEntity<List<Observacion>> ListarNarnacay() {
        try {
            List<Observacion> listaObservaciones = observacionService.listarNarancay();
            return ResponseEntity.ok(listaObservaciones);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @PutMapping("/agregarCorrecionNarancay/")
    public Observacion agregarCorrecionNarancay(@RequestBody ObservacionCorrecion obj){
        try{
            return observacionService.editarObservacionNarancay(obj.getObservacion(), obj.getCorreccion());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/excel/narancay")
    public ResponseEntity<InputStreamResource> excelNarancay() throws Exception {
        ByteArrayInputStream stream = observacionService.exportarExcelNarancay();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=observaciones.xlsx");
        headers.add("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"); // Tipo de contenido correcto

        return ResponseEntity.ok()
                .headers(headers)
                .body(new InputStreamResource(stream));
    }

    /**
     * Crud para la bodega Dañados
     */
    @PostMapping("/guardar/bodDañados")
    public ResponseEntity<Observacion> guardarDañados(@RequestBody Observacion observacion){
        try {
            Observacion observacion1 = observacionService.guardarobservacionBodDanados(observacion);
            return ResponseEntity.ok(observacion1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/listarBodDañados")
    public ResponseEntity<List<Observacion>> ListarDañados() {
        try {
            List<Observacion> listaObservaciones = observacionService.listarBodDañados();
            return ResponseEntity.ok(listaObservaciones);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @PutMapping("/agregarCorrecionBodDañados/")
    public Observacion agregarCorrecionDañados(@RequestBody ObservacionCorrecion obj){
        try{
            return observacionService.editarObservacionBodDa(obj.getObservacion(), obj.getCorreccion());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/excel/bodDañados/")
    public ResponseEntity<InputStreamResource> exportarBodDañados() throws Exception {
        ByteArrayInputStream stream = observacionService.exportarExcelBodDa();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=observaciones.xlsx");
        headers.add("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"); // Tipo de contenido correcto

        return ResponseEntity.ok()
                .headers(headers)
                .body(new InputStreamResource(stream));
    }

    /**
     * Crud para la bodega Gran Colombia P1
     */
    @PostMapping("/guardar/gColombia")
    public ResponseEntity<Observacion> guardarGColombia(@RequestBody Observacion observacion){
        try {
            Observacion observacion1 = observacionService.guardarObservacionGColombia(observacion);
            return ResponseEntity.ok(observacion1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/listarGColombia")
    public ResponseEntity<List<Observacion>> ListarGColombia() {
        try {
            List<Observacion> listaObservaciones = observacionService.listarGColombia();
            return ResponseEntity.ok(listaObservaciones);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @PutMapping("/agregarCorrecionGColombia/")
    public Observacion agregarCorrecionGColombia(@RequestBody ObservacionCorrecion obj){
        try{
            return observacionService.editarObservacionGColombia(obj.getObservacion(), obj.getCorreccion());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/excel/gColombia/")
    public ResponseEntity<InputStreamResource> exportarGColombia() throws Exception {
        ByteArrayInputStream stream = observacionService.exportarExcelGColombia();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=observaciones.xlsx");
        headers.add("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"); // Tipo de contenido correcto

        return ResponseEntity.ok()
                .headers(headers)
                .body(new InputStreamResource(stream));
    }

    /**
     * Crud para la bodega Gran Colombia P2
     */
    @PostMapping("/guardar/gColombia2")
    public ResponseEntity<Observacion> guardarGColombia2(@RequestBody Observacion observacion){
        try {
            Observacion observacion1 = observacionService.guardarObservacionGColombia2(observacion);
            return ResponseEntity.ok(observacion1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/listarGColombia2")
    public ResponseEntity<List<Observacion>> ListarGColombia2() {
        try {
            List<Observacion> listaObservaciones = observacionService.listarGColombia2();
            return ResponseEntity.ok(listaObservaciones);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @PutMapping("/agregarCorrecionGColombia2/")
    public Observacion agregarCorrecionGColombia2(@RequestBody ObservacionCorrecion obj){
        try{
            return observacionService.editarObservacionGColombia2(obj.getObservacion(), obj.getCorreccion());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/excel/gColombia2/")
    public ResponseEntity<InputStreamResource> exportarGColombia2() throws Exception {
        ByteArrayInputStream stream = observacionService.exportarExcelGColombia2();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=observaciones.xlsx");
        headers.add("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"); // Tipo de contenido correcto

        return ResponseEntity.ok()
                .headers(headers)
                .body(new InputStreamResource(stream));
    }

    /**
     * Crud para la bodega Vergel
     */
    @PostMapping("/guardar/vergel")
    public ResponseEntity<Observacion> guardarVergel(@RequestBody Observacion observacion){
        try {
            Observacion observacion1 = observacionService.guardarObservacionVergel(observacion);
            return ResponseEntity.ok(observacion1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/listarVergel")
    public ResponseEntity<List<Observacion>> ListarVergel() {
        try {
            List<Observacion> listaObservaciones = observacionService.listarVergel();
            return ResponseEntity.ok(listaObservaciones);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @PutMapping("/agregarCorrecionVergel/")
    public Observacion agregarCorrecionVergel(@RequestBody ObservacionCorrecion obj){
        try{
            return observacionService.editarObservacionVergel(obj.getObservacion(), obj.getCorreccion());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/excel/vergel/")
    public ResponseEntity<InputStreamResource> exportarVergel() throws Exception {
        ByteArrayInputStream stream = observacionService.exportarExcelVergel();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=observaciones.xlsx");
        headers.add("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"); // Tipo de contenido correcto

        return ResponseEntity.ok()
                .headers(headers)
                .body(new InputStreamResource(stream));
    }
}
