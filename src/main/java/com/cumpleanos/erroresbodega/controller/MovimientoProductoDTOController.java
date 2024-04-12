/*
 * Copyright (c) 2024.
 *  Este código es propiedad de Luis Chumi y está protegido por las leyes de derechos de autor.
 *  Se concede el permiso para usar, copiar, modificar y distribuir este software con la condición de que se incluya este aviso en todas las copias o partes sustanciales del software.
 *  Para obtener ayuda, soporte o permisos adicionales, contacta a Luis Chumi en luischumi.9@gmail.com.
 */

package com.cumpleanos.erroresbodega.controller;

import com.cumpleanos.erroresbodega.models.storage.AgregarProductoRequest;
import com.cumpleanos.erroresbodega.models.storage.MovimientosProductosDTO;
import com.cumpleanos.erroresbodega.models.storage.ProductoDTO;
import com.cumpleanos.erroresbodega.services.MovimientosProductosDTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/movimiento/")
@CrossOrigin("*")
public class MovimientoProductoDTOController {

    @Autowired
    private MovimientosProductosDTOService movimientosService;

    @PostMapping("guardar")
    public ResponseEntity<MovimientosProductosDTO> guardar(@RequestBody MovimientosProductosDTO movimientosProductosDTO){
        try {
            MovimientosProductosDTO movimientoNuevo = movimientosService.guardar(movimientosProductosDTO);
            return ResponseEntity.ok(movimientoNuevo);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @GetMapping("listar")
    public ResponseEntity<List<MovimientosProductosDTO>> listar(){
        try {
            List<MovimientosProductosDTO> movimientos = movimientosService.listar();
            return ResponseEntity.ok(movimientos);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @PutMapping("agregarProducto")
    public ResponseEntity<MovimientosProductosDTO> agregarProducto(@RequestBody AgregarProductoRequest request){
        try {
            MovimientosProductosDTO movimiento= movimientosService.editarMovimiento(request.getMovimientosProductosDTO(), request.getProductoDTO());
            return ResponseEntity.ok(movimiento);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }


}
