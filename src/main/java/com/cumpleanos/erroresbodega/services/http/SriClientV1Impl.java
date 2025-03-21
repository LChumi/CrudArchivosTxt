/*
 * Copyright (c) 2025 Luis Chumi.
 * Este software está licenciado bajo la Licencia Pública General de GNU versión 3. Puedes encontrar una copia de la licencia en https://www.gnu.org/licenses/gpl-3.0.html.
 *
 * Para consultas o comentarios, puedes contactarme en "luischumi.9@gmail.com".
 * Me gustaría ser reconocido por mi trabajo y estar abierto a colaboraciones o enseñanzas sobre el programa.
 */

package com.cumpleanos.erroresbodega.services.http;

import com.cumpleanos.erroresbodega.exceptions.HttpResponseHandler;
import com.cumpleanos.erroresbodega.http.ClientSriV1;
import com.cumpleanos.erroresbodega.models.api.ContribuyenteSri;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class SriClientV1Impl {

    private final ClientSriV1 sriClient;

    public ContribuyenteSri getClient(String id){
        return HttpResponseHandler.handle(() -> sriClient.consultar(id),
                "Error al obtener el cliente con id: " + id);
    }
}
