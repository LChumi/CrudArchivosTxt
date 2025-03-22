/*
 * Copyright (c) 2024 Luis Chumi.
 * Este software está licenciado bajo la Licencia Pública General de GNU versión 3. Puedes encontrar una copia de la licencia en https://www.gnu.org/licenses/gpl-3.0.html.
 *
 * Para consultas o comentarios, puedes contactarme en "luischumi.9@gmail.com".
 * Me gustaría ser reconocido por mi trabajo y estar abierto a colaboraciones o enseñanzas sobre el programa.
 */

package com.cumpleanos.erroresbodega.controller;

import com.cumpleanos.erroresbodega.models.Cliente;
import com.cumpleanos.erroresbodega.models.api.ContribuyenteSri;
import com.cumpleanos.erroresbodega.models.api.PersonaSri;
import com.cumpleanos.erroresbodega.services.ClienteService;
import com.cumpleanos.erroresbodega.services.http.SriClientV1Impl;
import com.cumpleanos.erroresbodega.services.http.SriClientV2Impl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sri")
@CrossOrigin("*")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ConsumoSriController {

    private final SriClientV1Impl service1;
    private final SriClientV2Impl service2;
    private final ClienteService  clienteService;

    @GetMapping(value = "/ced-ruc/{ced}/{ident}")
    public String getCedRuc(@PathVariable String ced, @PathVariable String ident) {
        ced = ced.trim();
        ident = ident.trim().toUpperCase();
        try {
            PersonaSri p = service2.getClient(ced, ident);

            if (p == null || p.getNombreCompleto() == null || p.getNombreCompleto().isEmpty()) {
                ContribuyenteSri c = service1.getClient(ced);
                if (c == null || c.getContribuyente() == null || c.getContribuyente().getNombreComercial() == null) {
                    return "";
                }
                return c.getContribuyente().getNombreComercial();
            } else {
                return p.getNombreCompleto();
            }
        } catch (NullPointerException e) {
            return "Error: Valores nulos inesperados.";
        } catch (Exception e) {
            return "Error inesperado: " + e.getMessage();
        }
    }


    @GetMapping(value = "/cliente/{ced}/{ident}")
    public ResponseEntity<String> getNombres(@PathVariable String ced, @PathVariable String ident) {
        ced = ced.trim();
        ident = ident.trim().toUpperCase();
        try {
            PersonaSri p = service2.getClient(ced, ident);

            if (p == null || p.getNombreCompleto() == null || p.getNombreCompleto().isEmpty()) {
                ContribuyenteSri c = service1.getClient(ced);
                if (c == null || c.getContribuyente() == null || c.getContribuyente().getNombreComercial() == null) {
                    Cliente cliente = clienteService.buscarPorCedula(ced);
                    return ResponseEntity.ok((cliente == null) ? "" : cliente.getCliNombre());
                }
                return ResponseEntity.ok(c.getContribuyente().getNombreComercial());
            } else {
                return ResponseEntity.ok(p.getNombreCompleto());
            }
        } catch (Exception e) {
            // Log del error para facilitar su rastreo
            log.error("Error inesperado: ", e);
            return ResponseEntity.badRequest().body("Error inesperado");
        }
    }

}