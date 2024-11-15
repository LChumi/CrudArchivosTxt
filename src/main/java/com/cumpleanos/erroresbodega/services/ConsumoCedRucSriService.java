/*
 * Copyright (c) 2024 Luis Chumi.
 * Este software está licenciado bajo la Licencia Pública General de GNU versión 3. Puedes encontrar una copia de la licencia en https://www.gnu.org/licenses/gpl-3.0.html.
 *
 * Para consultas o comentarios, puedes contactarme en "luischumi.9@gmail.com".
 * Me gustaría ser reconocido por mi trabajo y estar abierto a colaboraciones o enseñanzas sobre el programa.
 */

package com.cumpleanos.erroresbodega.services;

import com.cumpleanos.erroresbodega.models.api.ContribuyenteSri;
import com.cumpleanos.erroresbodega.models.api.PersonaSri;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Collections;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConsumoCedRucSriService {

    @Value("${apiSri}")
    private String baseUrl;

    @Value("${apiSrid}")
    private String apiSri;

    private final RestTemplate restTemplate;

    //"+cedula+"&tipoIdentificacion="+tipo;
    public String getCedulaRuc(String cedula, String tipoIdentificacion){
            String url = baseUrl+cedula+"&tipoIdentificacion="+tipoIdentificacion;

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));

            HttpEntity<?> entity = new HttpEntity<>(headers);

            try {
                ResponseEntity<PersonaSri> response = restTemplate.getForEntity(url, PersonaSri.class);
                if (response.getStatusCode().is2xxSuccessful()){
                    PersonaSri persona =  response.getBody();
                    assert persona != null;
                    return persona.getNombreCompleto() + "|"+ persona.getIdentificacion();
                } else {
                    log.error("Error en la consulta del Sri: {}", response.getBody());
                    return "";
                }
            } catch (Exception e) {
                log.error("Error en el servidor del Sri", e);
                return "";
            }
    }

    public String getNombreByCedula(String cedula){
        String url = apiSri + cedula;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
        headers.setAccept(Collections.singletonList(MediaType.ALL));
        headers.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");
        HttpEntity<?> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<ContribuyenteSri> response = restTemplate.getForEntity(url, ContribuyenteSri.class);

            // Verifica si el código de estado HTTP es 200 OK
            if (response.getStatusCode().is2xxSuccessful()) {
                ContribuyenteSri contribuyente = response.getBody();
                assert contribuyente != null;
                return contribuyente.getContribuyente().getNombreComercial() + "|" + contribuyente.getContribuyente().getIdentificacion();
            } else {
                // Log error y manejo de redirección (Página de mantenimiento)
                log.error("Error en la respuesta del SRI: Código {}. Detalles: {}", response.getStatusCode(), response.getBody());
                if (response.getStatusCode().is3xxRedirection()) {
                    log.warn("Redirección detectada. El servidor está posiblemente en mantenimiento.");
                }
                return "Servicio no disponible. Intenta más tarde.";
            }
        } catch (HttpClientErrorException e) {
            // Manejo específico del error 406
            if (e.getStatusCode() == HttpStatus.NOT_ACCEPTABLE) {
                log.error("El servidor respondió con un error 406: No se puede aceptar el formato solicitado.", e);
                return "Error 406: El servidor no acepta el formato solicitado.";
            }
            log.error("Error en el servidor del SRI", e);
            return "Error al conectar con el servidor del SRI.";
        } catch (Exception e) {
            log.error("Error inesperado al consultar el SRI", e);
            return "Error inesperado al consultar el SRI.";
        }
    }

}
