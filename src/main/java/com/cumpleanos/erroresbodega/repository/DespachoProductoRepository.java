/*
 * Copyright (c) 2024 Luis Chumi.
 * Este software está licenciado bajo la Licencia Pública General de GNU versión 3.
 * Puedes encontrar una copia de la licencia en https://www.gnu.org/licenses/gpl-3.0.html.
 *
 * Para consultas o comentarios, puedes contactarme en luischumi.9@gmail.com
 */

package com.cumpleanos.erroresbodega.repository;

import com.cumpleanos.erroresbodega.models.DespachoProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.util.List;

public interface DespachoProductoRepository extends JpaRepository<DespachoProducto, String> {

    @Query("SELECT P FROM DespachoProducto P WHERE P.ccoCodigo =:ccoCodigo")
    List<DespachoProducto> findByCcoCodigo(BigInteger ccoCodigo);

}
