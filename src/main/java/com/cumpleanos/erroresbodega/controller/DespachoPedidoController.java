/*
 * Copyright (c) 2024 Luis Chumi.
 * Este software está licenciado bajo la Licencia Pública General de GNU versión 3. Puedes encontrar una copia de la licencia en https://www.gnu.org/licenses/gpl-3.0.html.
 *
 * Para consultas o comentarios, puedes contactarme en "luischumi.9@gmail.com".
 * Me gustaría ser reconocido por mi trabajo y estar abierto a colaboraciones o enseñanzas sobre el programa.
 */

package com.cumpleanos.erroresbodega.controller;

import com.cumpleanos.erroresbodega.models.DespachoPedido;
import com.cumpleanos.erroresbodega.services.DespachoPedidosService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("/pedidos/")
@CrossOrigin("*")
@Slf4j
public class DespachoPedidoController {

    @Autowired
    private DespachoPedidosService pedidosService;

    @GetMapping("listar/{pedido_interno}")
    public ResponseEntity<List<DespachoPedido>> listarPedidos(@PathVariable Long pedido_interno){
        try {
            List<DespachoPedido> pedidos = pedidosService.listarPedidos(pedido_interno);
            return ResponseEntity.ok(pedidos);
        }catch (Exception e){
            log.error("ERROR en servicio pedidos: {}",e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
