/*
 * Copyright (c) 2025 Luis Chumi.
 * Este software está licenciado bajo la Licencia Pública General de GNU versión 3. Puedes encontrar una copia de la licencia en https://www.gnu.org/licenses/gpl-3.0.html.
 *
 * Para consultas o comentarios, puedes contactarme en "luischumi.9@gmail.com".
 * Me gustaría ser reconocido por mi trabajo y estar abierto a colaboraciones o enseñanzas sobre el programa.
 */

package com.cumpleanos.erroresbodega.http;

import com.cumpleanos.erroresbodega.models.api.PersonaSri;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "sujeto-sri", url = "https://srienlinea.sri.gob.ec/sri-catastro-sujeto-servicio-internet/rest")
public interface ClientSriV2 {

    @GetMapping(value = "/Persona/obtenerPorTipoIdentificacion")
    ResponseEntity<PersonaSri> consultar(
            @RequestParam("numeroIdentificacion") String numeroIdentificacion,
            @RequestParam("tipoIdentificacion") String tipoIdentificacion
    );
}