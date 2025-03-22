/*
 * Copyright (c) 2025 Luis Chumi.
 * Este software está licenciado bajo la Licencia Pública General de GNU versión 3. Puedes encontrar una copia de la licencia en https://www.gnu.org/licenses/gpl-3.0.html.
 *
 * Para consultas o comentarios, puedes contactarme en "luischumi.9@gmail.com".
 * Me gustaría ser reconocido por mi trabajo y estar abierto a colaboraciones o enseñanzas sobre el programa.
 */

package com.cumpleanos.erroresbodega.http;

import com.cumpleanos.erroresbodega.models.api.ContribuyenteSri;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "sri-service", url = "https://srienlinea.sri.gob.ec/movil-servicios/api/v1.0")
public interface ClientSriV1 {

    @GetMapping(value = "/deudas/porIdentificacion/{id}")
    ResponseEntity<ContribuyenteSri> consultar(@PathVariable String id);
}
