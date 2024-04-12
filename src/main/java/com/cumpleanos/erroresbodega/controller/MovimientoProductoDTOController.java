/*
 * Copyright (c) 2024.
 *  Este código es propiedad de Luis Chumi y está protegido por las leyes de derechos de autor.
 *  Se concede el permiso para usar, copiar, modificar y distribuir este software con la condición de que se incluya este aviso en todas las copias o partes sustanciales del software.
 *  Para obtener ayuda, soporte o permisos adicionales, contacta a Luis Chumi en luischumi.9@gmail.com.
 */

package com.cumpleanos.erroresbodega.controller;

import com.cumpleanos.erroresbodega.models.storage.AgregarProductoRequest;
import com.cumpleanos.erroresbodega.models.storage.MovimientosProductosDTO;
import com.cumpleanos.erroresbodega.services.MovimientosProductosDTOServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/movimiento/")
@CrossOrigin("*")
public class MovimientoProductoDTOController {

    @Autowired
    private MovimientosProductosDTOServiceImpl service;

    @PostMapping("guardar/narancay")
    public ResponseEntity<MovimientosProductosDTO> guardarNarancay(@RequestBody MovimientosProductosDTO movimientosProductosDTO){
        try {
            MovimientosProductosDTO movimientoNuevo = service.guardarNarancay(movimientosProductosDTO);
            return ResponseEntity.ok(movimientoNuevo);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @GetMapping("listar/narancay")
    public ResponseEntity<List<MovimientosProductosDTO>> listarNarancay(){
        try {
            List<MovimientosProductosDTO> movimientos = service.listarNarancay();
            return ResponseEntity.ok(movimientos);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @PutMapping("agregarProducto/narancay")
    public ResponseEntity<MovimientosProductosDTO> agregarProductoNarancay(@RequestBody AgregarProductoRequest request){
        try {
            MovimientosProductosDTO movimiento= service.agregarProductoNarancay(request);
            return ResponseEntity.ok(movimiento);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @PostMapping("exportar/excel/narancay")
    public ResponseEntity<InputStreamResource> exportarExcelNarancay(@RequestBody MovimientosProductosDTO movimiento) throws IOException{
        ByteArrayInputStream stream = service.exportarExcelNarancay(movimiento);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename="+movimiento.getNombre()+".xlsx");
        headers.add("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"); // Tipo de contenido correcto

        return ResponseEntity.ok()
                .headers(headers)
                .body(new InputStreamResource(stream));
    }

    @PostMapping("guardar/Zhucay")
    public ResponseEntity<MovimientosProductosDTO> guardarZhucay(@RequestBody MovimientosProductosDTO movimientosProductosDTO){
        try {
            MovimientosProductosDTO movimientoNuevo = service.guardarZhucay(movimientosProductosDTO);
            return ResponseEntity.ok(movimientoNuevo);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @GetMapping("listar/zhucay")
    public ResponseEntity<List<MovimientosProductosDTO>> listarZhucay(){
        try {
            List<MovimientosProductosDTO> movimientos = service.listarZhucay();
            return ResponseEntity.ok(movimientos);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @PutMapping("agregarProducto/zhucay")
    public ResponseEntity<MovimientosProductosDTO> agregarProductoZhucay(@RequestBody AgregarProductoRequest request){
        try {
            MovimientosProductosDTO movimiento= service.agregarProductoZhucay(request);
            return ResponseEntity.ok(movimiento);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @PostMapping("exportar/excel/zhucay")
    public ResponseEntity<InputStreamResource> exportarExcelZhucay(@RequestBody MovimientosProductosDTO movimiento) throws IOException{
        ByteArrayInputStream stream = service.exportarExcelZhucay(movimiento);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename="+movimiento.getNombre()+".xlsx");
        headers.add("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"); // Tipo de contenido correcto

        return ResponseEntity.ok()
                .headers(headers)
                .body(new InputStreamResource(stream));
    }
}
