/*
 * Copyright (c) 2026 Luis Chumi.
 * Este software está licenciado bajo la Licencia Pública General de GNU versión 3. Puedes encontrar una copia de la licencia en https://www.gnu.org/licenses/gpl-3.0.html.
 *
 * Para consultas o comentarios, puedes contactarme en "luischumi.9@gmail.com".
 * Me gustaría ser reconocido por mi trabajo y estar abierto a colaboraciones o enseñanzas sobre el programa.
 */

package com.cumpleanos.erroresbodega.controller;

import com.cumpleanos.erroresbodega.models.dto.TransferenciaRequest;
import com.cumpleanos.erroresbodega.services.ConsignacionService;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consignacion")
@CrossOrigin("*")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ConsignacionController {

    private final ConsignacionService consignacionService;

    @PostMapping("/generar")
    public ResponseEntity<String> ejecutarTransferencia(@RequestBody TransferenciaRequest r) {

        String resultado = consignacionService.ejecutarTransferencia(
                r.empresa(),
                r.comprobante(),
                r.bodIni(),
                r.bodFin()
        );
        return ResponseEntity.ok(resultado);
    }
}