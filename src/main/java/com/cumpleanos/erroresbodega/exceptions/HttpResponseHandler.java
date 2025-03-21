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
        }catch(FeignException e){
            log.error("Error de Feign: {} - {} ",e.status(), e.getMessage());
            return null;
        }catch(Exception e){
            log.error(errorMessage, e);
            return null;
        }
    }
}
