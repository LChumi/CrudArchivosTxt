/*
 * Copyright (c) 2024 Luis Chumi.
 * Este software está licenciado bajo la Licencia Pública General de GNU versión 3. Puedes encontrar una copia de la licencia en https://www.gnu.org/licenses/gpl-3.0.html.
 *
 * Para consultas o comentarios, puedes contactarme en "luischumi.9@gmail.com".
 * Me gustaría ser reconocido por mi trabajo y estar abierto a colaboraciones o enseñanzas sobre el programa.
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
