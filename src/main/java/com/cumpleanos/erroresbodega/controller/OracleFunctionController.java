/*
 * Copyright (c) 2026 Luis Chumi.
 * Este software está licenciado bajo la Licencia Pública General de GNU versión 3. Puedes encontrar una copia de la licencia en https://www.gnu.org/licenses/gpl-3.0.html.
 *
 * Para consultas o comentarios, puedes contactarme en "luischumi.9@gmail.com".
 * Me gustaría ser reconocido por mi trabajo y estar abierto a colaboraciones o enseñanzas sobre el programa.
 */

package com.cumpleanos.erroresbodega.controller;

import com.cumpleanos.erroresbodega.models.dto.ValidarRecepcionResult;
import com.cumpleanos.erroresbodega.services.FunctionOracleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/fuction")
@RequiredArgsConstructor
public class OracleFunctionController {

    private final FunctionOracleService functionService;

    @GetMapping("/validar/recepcion/{empresa}/{creposicion}")
    public ResponseEntity<ValidarRecepcionResult> validarRecepcion(@PathVariable Long empresa, @PathVariable Long creposicion) {
        ValidarRecepcionResult result = functionService.validarRecepcion(empresa, creposicion);
        return ResponseEntity.ok(result);
    }
}
