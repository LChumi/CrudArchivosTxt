/*
 * Copyright (c) 2024 Luis Chumi.
 * Este software está licenciado bajo la Licencia Pública General de GNU versión 3. Puedes encontrar una copia de la licencia en https://www.gnu.org/licenses/gpl-3.0.html.
 *
 * Para consultas o comentarios, puedes contactarme en "luischumi.9@gmail.com".
 * Me gustaría ser reconocido por mi trabajo y estar abierto a colaboraciones o enseñanzas sobre el programa.
 */

package com.cumpleanos.erroresbodega.controller;

import com.cumpleanos.erroresbodega.models.storage.Calificacion;
import com.cumpleanos.erroresbodega.services.CalificacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/calificacion")
@RequiredArgsConstructor
@CrossOrigin("*")
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

    @GetMapping("/exportar/excel")
    public ResponseEntity<InputStreamResource> exportAllData() throws Exception {
        ByteArrayInputStream stream = calificacionService.exportarExcel();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=calificaciones.xlsx");
        headers.add("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"); // Tipo de contenido correcto

        return ResponseEntity.ok()
                .headers(headers)
                .body(new InputStreamResource(stream));
    }

}
