package com.cumpleanos.erroresbodega.repository;

import com.cumpleanos.erroresbodega.models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto,String> {

    @Query("SELECT P FROM Producto P WHERE P.bod_codigo = :bod_codigo AND (P.pro_id LIKE %:data% OR P.pro_id1 LIKE %:data%)")
    Producto getByPro_idOrPro_id1(@Param("bod_codigo")Long bod_codigo,@Param("data") String data);
}
