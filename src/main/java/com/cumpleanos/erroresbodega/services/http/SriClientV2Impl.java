/*
 * Copyright (c) 2025 Luis Chumi.
 * Este software está licenciado bajo la Licencia Pública General de GNU versión 3. Puedes encontrar una copia de la licencia en https://www.gnu.org/licenses/gpl-3.0.html.
 *
 * Para consultas o comentarios, puedes contactarme en "luischumi.9@gmail.com".
 * Me gustaría ser reconocido por mi trabajo y estar abierto a colaboraciones o enseñanzas sobre el programa.
 */

package com.cumpleanos.erroresbodega.services.http;

import com.cumpleanos.erroresbodega.exceptions.HttpResponseHandler;
import com.cumpleanos.erroresbodega.http.ClientSriV2;
import com.cumpleanos.erroresbodega.models.api.PersonaSri;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class SriClientV2Impl {

    private final ClientSriV2 sriClient;

    public PersonaSri getClient(String id, String tipo){
        return HttpResponseHandler.handle(() -> sriClient.consultar(id,tipo),
                "Error al obtener el cliente con id: " + id);
    }
}
