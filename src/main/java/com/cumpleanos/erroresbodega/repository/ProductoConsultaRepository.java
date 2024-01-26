/*
 * Copyright (c) 2024.
 *  Este código es propiedad de Luis Chumi y está protegido por las leyes de derechos de autor.
 *  Se concede el permiso para usar, copiar, modificar y distribuir este software con la condición de que se incluya este aviso en todas las copias o partes sustanciales del software.
 *  Para obtener ayuda, soporte o permisos adicionales, contacta a Luis Chumi en luischumi.9@gmail.com.
 */

package com.cumpleanos.erroresbodega.repository;

import com.cumpleanos.erroresbodega.models.Producto;
import com.cumpleanos.erroresbodega.models.dto.ProductoSugeridoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoConsultaRepository extends JpaRepository<Producto,Long> {

    @Query("SELECT new ProductoSugeridoDTO(" +
            "p.proId," +
            " p.proId1," +
            " p.proNombre," +
            " imp.iitCantLiquidada, " +
            "imp.iitPrecio," +
            " imp.iitCosto," +
            " cco.ccoFecha," +
            " cli.cliNombre," +
            "cli.cliDireccion) " +
            "FROM Producto p, ImporItem imp, CComproba cco, Cliente cli " +
            "WHERE p.proId = :barraId " +
            "AND p.proCodigo = imp.iitProducto " +
            "AND p.proEmpresa = imp.iitEmpresa " +
            "AND imp.iitImpComproba = cco.ccoCodigo " +
            "AND p.proProveedor = cli.cliCodigo " +
            "AND imp.iitEmpresa = cco.ccoEmpresa")
    List<ProductoSugeridoDTO> obtenerDetallesProducto(@Param("barraId") String barraId);
}
