/*
 * Copyright (c) 2024 Luis Chumi.
 * Este software está licenciado bajo la Licencia Pública General de GNU versión 3.
 * Puedes encontrar una copia de la licencia en https://www.gnu.org/licenses/gpl-3.0.html.
 *
 * Para consultas o comentarios, puedes contactarme en luischumi.9@gmail.com
 */

package com.cumpleanos.erroresbodega.controller;

import com.cumpleanos.erroresbodega.models.Cliente;
import com.cumpleanos.erroresbodega.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cliente")
@CrossOrigin("*")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/buscar/{cedula}")
    public ResponseEntity<Cliente> buscar(@PathVariable String cedula){System.out.println(cedula);

            Cliente cliente= clienteService.buscarPorCedula(cedula);
            return ResponseEntity.ok(cliente);
    }
}
