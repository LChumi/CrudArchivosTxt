/*
 * Copyright (c) 2024 Luis Chumi.
 * Este software está licenciado bajo la Licencia Pública General de GNU versión 3. Puedes encontrar una copia de la licencia en https://www.gnu.org/licenses/gpl-3.0.html.
 *
 * Para consultas o comentarios, puedes contactarme en "luischumi.9@gmail.com".
 * Me gustaría ser reconocido por mi trabajo y estar abierto a colaboraciones o enseñanzas sobre el programa.
 */

package com.cumpleanos.erroresbodega.services;

import com.cumpleanos.erroresbodega.models.DespachoPedido;
import com.cumpleanos.erroresbodega.repository.DespachoPedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DespachoPedidosServiceImpl implements DespachoPedidosService {

    @Autowired
    private DespachoPedidoRepository pedidoRepository;

    @Override
    public List<DespachoPedido> listarPedidos(Long pedido_interno) {
        Long estado =2L;
        return pedidoRepository.findByEstadoAndPedido_interno(estado,pedido_interno);
    }
}
