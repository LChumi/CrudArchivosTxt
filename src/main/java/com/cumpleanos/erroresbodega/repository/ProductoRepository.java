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
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductoRepository extends JpaRepository<ProductoView,String>, JpaSpecificationExecutor<ProductoView> {

    @Query("SELECT p FROM ProductoView p WHERE p.bodCodigo = :bod_codigo AND (p.proId LIKE %:data% OR p.proId1 LIKE %:data%)")
    List<ProductoView> getByProIdOrPro_id1(@Param("bod_codigo")Long bod_codigo, @Param("data") String data);

    @Query("SELECT p FROM ProductoView p WHERE p.bodCodigo=:bodega AND (p.proId LIKE %:data% OR p.proId1 LIKE %:data%)")
    List<ProductoView> findByProIdAndBodCodigo(Long bodega, String data);

    List<ProductoView> findByProIdAndProId1AndBodCodigo(String barra, String item, Long bodega);

}
