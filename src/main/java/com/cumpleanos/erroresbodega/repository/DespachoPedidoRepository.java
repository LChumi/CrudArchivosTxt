/*
 * Copyright (c) 2024 Luis Chumi.
 * Este software está licenciado bajo la Licencia Pública General de GNU versión 3.
 * Puedes encontrar una copia de la licencia en https://www.gnu.org/licenses/gpl-3.0.html.
 *
 * Para consultas o comentarios, puedes contactarme en luischumi.9@gmail.com
 */

package com.cumpleanos.erroresbodega.repository;

import com.cumpleanos.erroresbodega.models.DespachoPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DespachoPedidoRepository extends JpaRepository<DespachoPedido, String> {

    @Query("SELECT p FROM DespachoPedido p WHERE p.estado =:estado AND p.pedido_interno =:pedido ")
    List<DespachoPedido> findByEstadoAndPedido_interno(Long estado, Long pedido);
}
