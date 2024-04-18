/*
 * Copyright (c) 2024 Luis Chumi.
 * Este software está licenciado bajo la Licencia Pública General de GNU versión 3. Puedes encontrar una copia de la licencia en https://www.gnu.org/licenses/gpl-3.0.html.
 *
 * Para consultas o comentarios, puedes contactarme en "luischumi.9@gmail.com".
 * Me gustaría ser reconocido por mi trabajo y estar abierto a colaboraciones o enseñanzas sobre el programa.
 */

package com.cumpleanos.erroresbodega.repository;

import com.cumpleanos.erroresbodega.models.ProductoView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductoRepository extends JpaRepository<ProductoView,String> {

    @Query("SELECT p FROM ProductoView p WHERE p.bod_codigo = :bod_codigo AND (p.pro_id LIKE %:data% OR p.pro_id1 LIKE %:data%)")
    List<ProductoView> getByPro_idOrPro_id1(@Param("bod_codigo")Long bod_codigo, @Param("data") String data);

    @Query("SELECT p FROM ProductoView p WHERE p.bod_codigo=:bodega AND (p.pro_id LIKE %:data% OR p.pro_id1 LIKE %:data%)")
    List<ProductoView> findByPro_idAndBod_codigo(Long bodega, String data);
}
