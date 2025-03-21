/*
 * Copyright (c) 2024 Luis Chumi.
 * Este software está licenciado bajo la Licencia Pública General de GNU versión 3. Puedes encontrar una copia de la licencia en https://www.gnu.org/licenses/gpl-3.0.html.
 *
 * Para consultas o comentarios, puedes contactarme en "luischumi.9@gmail.com".
 * Me gustaría ser reconocido por mi trabajo y estar abierto a colaboraciones o enseñanzas sobre el programa.
 */

package com.cumpleanos.erroresbodega.controller;

import com.cumpleanos.erroresbodega.models.Cliente;
import com.cumpleanos.erroresbodega.services.ClienteService;
import com.cumpleanos.erroresbodega.services.ConsumoCedRucSriService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sri")
@CrossOrigin("*")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ConsumoSriController {

    private final ConsumoCedRucSriService service;
    private final ClienteService  clienteService;

    @GetMapping(value = "/ced-ruc/{ced}/{ident}")
    public String getCedRuc(@PathVariable String ced, @PathVariable String ident) {
        String response = "";
        try{
            response = service.getNombreByCedula(ced);
            if (response.contains("Error")) {
                response = service.getCedulaRuc(ced, ident);
            }
            return response;
        } catch (Exception e) {
            return "";
        }
    }

    @GetMapping(value = "/cliente/{ced}/{ident}")
    public String getNombres(@PathVariable String ced, @PathVariable String ident) {
        try {
            String response = service.getNombreByCedula(ced);

            if (response.contains("Error")) {
                response = service.getCedulaRuc(ced, ident);

                if (response.isEmpty()) {
                    Cliente cliente = clienteService.buscarPorCedula(ced);
                    return (cliente == null) ? "" : cliente.getCliNombre();
                }
            }
            return response;
        } catch (Exception e) {
            // Log del error para facilitar su rastreo
            log.error("Error inesperado: ", e);
            return "Error inesperado";
        }
    }

}