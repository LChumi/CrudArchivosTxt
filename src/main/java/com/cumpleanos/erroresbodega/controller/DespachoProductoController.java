/*
 * Copyright (c) 2024 Luis Chumi.
 * Este software está licenciado bajo la Licencia Pública General de GNU versión 3. Puedes encontrar una copia de la licencia en https://www.gnu.org/licenses/gpl-3.0.html.
 *
 * Para consultas o comentarios, puedes contactarme en "luischumi.9@gmail.com".
 * Me gustaría ser reconocido por mi trabajo y estar abierto a colaboraciones o enseñanzas sobre el programa.
 */

package com.cumpleanos.erroresbodega.controller;

import com.cumpleanos.erroresbodega.models.DespachoProducto;
import com.cumpleanos.erroresbodega.services.DespachoPedidosService;
import com.cumpleanos.erroresbodega.services.DespachoProductoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("/despacho_producto/")
@CrossOrigin("*")
@Slf4j
public class DespachoProductoController {

    @Autowired
    private DespachoProductoService productoService;

    @GetMapping("listar/{cco}")
    public ResponseEntity<List<DespachoProducto>> listar(@PathVariable BigInteger cco){
        try {
            List<DespachoProducto> productos = productoService.listarProductos(cco);
            return ResponseEntity.ok(productos);
        }catch (Exception e){
            log.error("ERROR en el servicio productos desapcho: {}",e.getMessage(),e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("producto/{cco}")
    public ResponseEntity<DespachoProducto> burcarProducto(@PathVariable BigInteger cco, @RequestParam String data){
        try {
            DespachoProducto producto = productoService.producto(cco, data);
            return ResponseEntity.ok(producto);
        }catch (Exception e){
            log.error("Error al buscar el producto: {} ", e.getMessage(),e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
