/*
 * Copyright (c) 2025 Luis Chumi.
 * Este software está licenciado bajo la Licencia Pública General de GNU versión 3. Puedes encontrar una copia de la licencia en https://www.gnu.org/licenses/gpl-3.0.html.
 *
 * Para consultas o comentarios, puedes contactarme en "luischumi.9@gmail.com".
 * Me gustaría ser reconocido por mi trabajo y estar abierto a colaboraciones o enseñanzas sobre el programa.
 */

package com.cumpleanos.erroresbodega.services;

import com.cumpleanos.erroresbodega.http.ClientSriV1;
import com.cumpleanos.erroresbodega.http.ClientSriV2;
import com.cumpleanos.erroresbodega.models.api.ContribuyenteSri;
import com.cumpleanos.erroresbodega.models.api.PersonaSri;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class SriServiceWrapper {

    private final ClientSriV1 clientSriV1;
    private final ClientSriV2 clientSriV2;

    @TimeLimiter(name = "sriTimeout", fallbackMethod = "fallbackPersona")
    public CompletableFuture<PersonaSri> getPersona(String ced, String ident){
        return CompletableFuture.supplyAsync(() ->
                clientSriV2.consultar(ced, ident).getBody()
        );
    }

    @TimeLimiter(name = "sriTimeout", fallbackMethod = "fallbackContribuyente")
    public CompletableFuture<ContribuyenteSri> getContribuyente(String ced){
        return CompletableFuture.supplyAsync(() ->
                clientSriV1.consultar(ced).getBody()
        );
    }

    private CompletableFuture<PersonaSri> fallbackPersona(String ced, String ident, Throwable ex) {
        return CompletableFuture.completedFuture(null);
    }

    private CompletableFuture<ContribuyenteSri> fallbackContribuyente(String ced, Throwable ex) {
        return CompletableFuture.completedFuture(null);
    }

}
