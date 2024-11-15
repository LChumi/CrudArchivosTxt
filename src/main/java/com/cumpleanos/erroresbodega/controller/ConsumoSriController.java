/*
 * Copyright (c) 2024 Luis Chumi.
 * Este software está licenciado bajo la Licencia Pública General de GNU versión 3. Puedes encontrar una copia de la licencia en https://www.gnu.org/licenses/gpl-3.0.html.
 *
 * Para consultas o comentarios, puedes contactarme en "luischumi.9@gmail.com".
 * Me gustaría ser reconocido por mi trabajo y estar abierto a colaboraciones o enseñanzas sobre el programa.
 */

package com.cumpleanos.erroresbodega.controller;

import com.cumpleanos.erroresbodega.services.ConsumoCedRucSriService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sri")
@CrossOrigin("*")
public class ConsumoSriController {

    @Autowired
    private ConsumoCedRucSriService service;

    @GetMapping(value = "/ced-ruc/{ced}/{ident}")
    public String getCedRuc(@PathVariable String ced, @PathVariable String ident) {
        String response = "";
        try{
            response = service.getNombreByCedula(ced);
            return response;
        } catch (Exception e) {
            return "";
        }
    }
}