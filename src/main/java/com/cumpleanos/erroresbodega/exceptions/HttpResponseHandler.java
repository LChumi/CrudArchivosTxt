/*
 * Copyright (c) 2025 Luis Chumi.
 * Este software está licenciado bajo la Licencia Pública General de GNU versión 3. Puedes encontrar una copia de la licencia en https://www.gnu.org/licenses/gpl-3.0.html.
 *
 * Para consultas o comentarios, puedes contactarme en "luischumi.9@gmail.com".
 * Me gustaría ser reconocido por mi trabajo y estar abierto a colaboraciones o enseñanzas sobre el programa.
 */

package com.cumpleanos.erroresbodega.exceptions;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

import java.util.function.Supplier;

@Slf4j
public class HttpResponseHandler {

    public static <T> T handle(Supplier<ResponseEntity<T>> supplier, String errorMessage){
        try{
            return supplier.get().getBody();
        }catch (FeignException e) {
            if (e.status() == 503) {
                log.error("Error: Servicio del SRI no disponible (503).");
                return null;
            } else if (e.status() == 500) {
                log.error("Error: Fallo interno en el SRI (500).");
                return null;
            } else if (e.status() == -1) {
                log.error("Error: Timeout al conectar con el SRI.");
                return null;
            }
            log.error("Error Feign: {}", e.getMessage());
            return null;
        }
        catch(Exception e){
            log.error(errorMessage, e);
            return null;
        }
    }
}
