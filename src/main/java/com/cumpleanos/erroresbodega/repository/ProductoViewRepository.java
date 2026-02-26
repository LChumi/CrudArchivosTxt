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

public interface ProductoViewRepository extends JpaRepository<ProductoView, String>, JpaSpecificationExecutor<ProductoView> {

    @Query("SELECT p FROM ProductoView p WHERE p.bodCodigo = :bod_codigo AND (p.proId LIKE %:data% OR p.proId1 LIKE %:data%)")
    List<ProductoView> getByProIdOrPro_id1(@Param("bod_codigo") Long bod_codigo, @Param("data") String data);

    @Query("SELECT p FROM ProductoView p WHERE p.bodCodigo=:bodega AND (p.proId LIKE %:data% OR p.proId1 LIKE %:data%)")
    List<ProductoView> findByProIdAndBodCodigo(Long bodega, String data);

    List<ProductoView> findByProIdAndProId1AndBodCodigo(String barra, String item, Long bodega);

    @Query(value = """
            SELECT 
                   MAX(PRG_USR.KP_PRODUCTO.FECHA_VENTA_PROD_EMPRESAS(
                        PN_EMPRESA  => V.PRO_EMPRESA,
                        PN_PRODUCTO => V.PRO_CODIGO,
                        PN_BODEGA   => V.BOD_CODIGO)) AS ultVenta,
            
                   MAX(PRG_USR.KP_PRODUCTO.FECHA_ULTIMA_COMPRA_EMPRESAS(
                        PN_EMPRESA  => V.PRO_EMPRESA,
                        PN_PRODUCTO => V.PRO_CODIGO,
                        PN_FECHA    => SYSDATE,
                        PN_BODEGA   => V.BOD_CODIGO,
                        PN_TIPO_ING => 'c')) AS ultComp,
            
                   MAX(PRG_USR.KP_PRODUCTO.TOT_COMPRAS_EMPRESAS(
                        PN_EMPRESA   => V.PRO_EMPRESA,
                        PN_FECHA_INI => TRUNC(SYSDATE, 'MM'),
                        PN_FECHA_FIN => TRUNC(SYSDATE),
                        PN_TIPO_ING  => 'c',
                        PN_TIPO_SAL  => 'm',
                        PN_PRODUCTO  => V.PRO_CODIGO)) AS ultCantCom,
            
                   MAX(PRG_USR.KP_PRODUCTO.TOT_COMPRAS_EMPRESAS(
                        PN_EMPRESA   => V.PRO_EMPRESA,
                        PN_FECHA_INI => TRUNC(SYSDATE, 'MM'),
                        PN_FECHA_FIN => TRUNC(SYSDATE),
                        PN_TIPO_ING  => 'c',
                        PN_TIPO_SAL  => 'u',
                        PN_PRODUCTO  => V.PRO_CODIGO)) AS costoCom,
            
                   MAX(PRG_USR.KP_PRODUCTO.DEVUELVE_VENTAS_FEC_EMP(
                        PN_EMPRESA  => V.PRO_EMPRESA,
                        PN_PRODUCTO => V.PRO_CODIGO,
                        PN_FECHA1   => TRUNC(SYSDATE, 'MM'),
                        PN_FECHA2   => TRUNC(SYSDATE),
                        PN_ALMACEN  => V.BOD_CODIGO)) AS cantVenta,
            
                   MAX(PRG_USR.KP_PRODUCTO.PRODUCTO_STOCK_FEC_EMP(
                        PN_EMPRESA  => V.PRO_EMPRESA,
                        PN_PRODUCTO => V.PRO_CODIGO,
                        PN_BODEGA   => V.BOD_CODIGO,
                        PN_FECHA    => TRUNC(SYSDATE) - 1,
                        PN_TIPO     => 1)) AS stockIni,
            
                   CL.CLI_NOMBRE AS cliNombre,
                   V.PRO_ID AS barra,
                   V.PRO_ID1 AS item,
                   V.PRO_NOMBRE AS proNombre,
                   MAX(V.STOCK_DISP) AS stockDisp,
                   MAX(V.STOCK_REAL) AS stockReal,
                   MAX(V.PVP) AS pvp
            
            FROM INV_PRODINFGEN_WEB_V V
            
            JOIN PRODUCTO PR
              ON PR.PRO_EMPRESA = V.PRO_EMPRESA
             AND PR.PRO_CODIGO  = V.PRO_CODIGO
            
            LEFT JOIN CLIENTE CL
              ON CL.CLI_EMPRESA = PR.PRO_EMPRESA
             AND CL.CLI_CODIGO  = PR.PRO_PROVEEDOR
            
            WHERE V.PRO_EMPRESA = 2
              AND V.BOD_TIPO IN (2,3)
              AND V.BOD_CODIGO = 10000601
            
              AND EXISTS (
                    SELECT 1
                    FROM CCOMPROBA C
                    JOIN DMOVINV D
                      ON D.DMO_EMPRESA = C.CCO_EMPRESA
                     AND D.DMO_CMO_COMPROBA = C.CCO_CODIGO
                    JOIN CLIENTE CL2
                      ON CL2.CLI_EMPRESA = C.CCO_EMPRESA
                     AND CL2.CLI_CODIGO  = C.CCO_CODCLIPRO
                    WHERE C.CCO_EMPRESA = 2
                      AND D.DMO_PRODUCTO = V.PRO_CODIGO
                      AND UPPER(CL2.CLI_NOMBRE) LIKE '%' || UPPER(:nombre) || '%'
                      AND C.CCO_TIPODOC = 29
                      AND C.CCO_ESTADO IN (2,3)
               )
            
            GROUP BY 
                   V.PRO_ID, 
                   V.PRO_ID1, 
                   V.PRO_NOMBRE, 
                   CL.CLI_NOMBRE
            """, nativeQuery = true)
    List<Object[]> obtenerReporte(@Param("nombre") String nombre);
}