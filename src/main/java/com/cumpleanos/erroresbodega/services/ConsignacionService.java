/*
 * Copyright (c) 2026 Luis Chumi.
 * Este software está licenciado bajo la Licencia Pública General de GNU versión 3. Puedes encontrar una copia de la licencia en https://www.gnu.org/licenses/gpl-3.0.html.
 *
 * Para consultas o comentarios, puedes contactarme en "luischumi.9@gmail.com".
 * Me gustaría ser reconocido por mi trabajo y estar abierto a colaboraciones o enseñanzas sobre el programa.
 */

package com.cumpleanos.erroresbodega.services;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Types;

@Service
@RequiredArgsConstructor
public class ConsignacionService {

    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public String ejecutarTransferencia(Long empresa, BigDecimal comprobante, Long bodIni, Long bodFin) {

        return jdbcTemplate.execute((ConnectionCallback<String>) connection -> {
            String sql = """
                    DECLARE
                                  PN_EMPRESA   NUMBER;
                                  PN_COMPROBA  NUMBER;
                                  PN_BOD_INI   NUMBER;
                                  PN_BOD_FIN   NUMBER;
                    
                                  V_RESULTADO VARCHAR2(250);
                    
                                  PN_TIPODOC  NUMBER := 41;
                                  V_CUENTA   NUMBER;
                                  V_CTA_INV  NUMBER;
                    
                                  V_CONCEPTO VARCHAR2(150) := 'EGRESO BODEGA CONSIGNACION';
                                  V_SIGLA    NUMBER;
                                    V_NUMERO   NUMBER;
                                    V_CODIGO   CCOMPROBA.CCO_CODIGO%TYPE;
                                    V_FECHA    DATE := trunc(SYSDATE);
                                    v_periodo  NUMBER := TO_CHAR(V_FECHA,'YYYY');
                                    V_DIA      NUMBER := TO_CHAR(V_FECHA,'DD');
                                    V_MES      NUMBER := TO_CHAR(V_FECHA,'MM');
                                    V_SECUENCIA NUMBER := 1;
                                    --
                                    CURSOR C_TIPODOC IS
                                      SELECT C.CTI_CODIGO, DT.DTI_NUMERO
                                        FROM DTIPODOC D, CTIPOCOM C, DTIPOCOM DT
                                       WHERE D.DTP_EMPRESA = PN_EMPRESA
                                         AND D.DTP_TPD_CODIGO = 30
                                         AND DT.DTI_SERIE = PN_BOD_INI
                                         AND C.CTI_EMPRESA = D.DTP_EMPRESA
                                         AND C.CTI_CODIGO = D.DTP_CTI_CODIGO
                                         AND DT.DTI_EMPRESA = C.CTI_EMPRESA
                                         AND DT.DTI_CTI_CODIGO = C.CTI_CODIGO;
                    
                                    CURSOR c_productos IS
                                      SELECT SUB.PRO_CODIGO,
                                             0 AS SAL_COSTO,
                                             0 SAL_TOTAL,
                                             SUB.CUE_CODIGO,
                                             SUB.PRO_UNIDAD,
                                             2 SAL_DEBCRE,
                                             SUB.BOD_CODIGO,
                                             SUM(SUB.CANTIDAD) SAL_CANTIDAD
                                        FROM (SELECT P.PRO_LINEA,
                                                     L.LIN_ID,
                                                     P.PRO_ID,
                                                     P.PRO_ID1,
                                                     P.PRO_NOMBRE,
                                                     G.GPR_NOMBRE,
                                                     G.GPR_CTA_CTR AS CUE_CODIGO,
                                                     T.TPR_NOMBRE,
                                                     P.PRO_EMPRESA,
                                                     P.PRO_CODIGO,
                                                     P.PRO_SECCION,
                                                     P.PRO_CARGA_WEB,
                                                     P.PRO_FOTO,
                                                     P.PRO_PRECIO1 PVP,
                                                     P.PRO_PRECIO2 PVD,
                                                     P.PRO_PRECIO3 PVS,
                                                     PN_BOD_INI BOD_CODIGO,
                                                     (SELECT U.UMD_CODIGO
                                                        FROM UMEDIDA U, FACTOR F
                                                       WHERE U.UMD_EMPRESA = P.PRO_EMPRESA
                                                         AND U.UMD_CODIGO = P.PRO_UNIDAD
                                                         AND F.FAC_EMPRESA = U.UMD_EMPRESA
                                                         AND F.FAC_PRODUCTO = P.PRO_CODIGO
                                                         AND F.FAC_UNIDAD = U.UMD_CODIGO
                                                         AND F.FAC_DEFAULT = 1) PRO_UNIDAD,
                                                     CANTIDADXBULTO(P.PRO_EMPRESA, P.PRO_CODIGO) CXB,
                                                     (SELECT SUM(d.dmo_cdigitada) CANT
                                                        FROM dmovinv d, producto pr
                                                       WHERE d.dmo_cmo_comproba IN
                                                             (PN_COMPROBA)
                                                         AND pr.pro_empresa = d.dmo_empresa
                                                         AND pr.pro_codigo = d.dmo_producto
                                                         AND pr.pro_id = p.pro_id) cantidad,
                                                     P.ROWID
                                                FROM PRODUCTO P, LINEA L, GPRODUCTO G, TPRODUCTO T
                                               WHERE P.PRO_EMPRESA = PN_EMPRESA
                                                 AND L.LIN_EMPRESA(+) = P.PRO_EMPRESA
                                                 AND L.LIN_CODIGO(+) = P.PRO_LINEA
                                                 AND G.GPR_EMPRESA = P.PRO_EMPRESA
                                                 AND G.GPR_CODIGO = P.PRO_GPRODUCTO
                                                 AND T.TPR_EMPRESA(+) = P.PRO_EMPRESA
                                                 AND T.TPR_CODIGO(+) = P.PRO_TPRODUCTO
                                                 AND P.PRO_ID IN
                                                     (SELECT p.pro_id
                                                        FROM dmovinv d, producto p
                                                       WHERE d.dmo_cmo_comproba IN
                                                             (PN_COMPROBA)
                                                         AND p.pro_empresa = d.dmo_empresa
                                                         AND p.pro_codigo = d.dmo_producto)) SUB
                                       GROUP BY SUB.PRO_CODIGO, SUB.CUE_CODIGO, SUB.PRO_UNIDAD, SUB.BOD_CODIGO;
                    
                                  BEGIN
                    
                                     PN_EMPRESA  := ?;
                                     PN_COMPROBA := ?;
                                     PN_BOD_INI  := ?;
                                     PN_BOD_FIN  := ?;
                                    
                                    -- CONFIGURACION POR EMPRESA
                                    IF PN_EMPRESA = 2 THEN
                                         V_CUENTA  := 10031560;
                                         V_CTA_INV := 22;
                                      ELSIF PN_EMPRESA = 3 THEN
                                         V_CUENTA  := 10031465;
                                         V_CTA_INV := 34;
                                      ELSE
                                         RAISE_APPLICATION_ERROR(-20001, 'Empresa no configurada');
                                      END IF;
                    
                                    -- INSERTA CABECERA
                                    OPEN C_TIPODOC;
                                    FETCH C_TIPODOC
                                     INTO V_SIGLA, V_NUMERO;
                                    CLOSE C_TIPODOC;
                    
                                    SELECT CCOMPROBA_S_CODIGO.NEXTVAL INTO V_CODIGO FROM DUAL;
                                    IF PN_BOD_INI > 0 THEN
                                      V_CODIGO := V_CODIGO + (1 * (POWER(10, 26)));
                                    END IF;
                    
                                    V_NUMERO := V_NUMERO + 1;
                                    IF PN_COMPROBA IS NOT NULL THEN
                                       V_CONCEPTO := V_CONCEPTO||' REF: '||AST_GEN.numero_comprobante(PN_EMPRESA, PN_COMPROBA);
                                    END IF;
                                    INSERT INTO CCOMPROBA
                                           (CCO_EMPRESA  , CCO_CODIGO  , CCO_PERIODO , CCO_SIGLA      , CCO_SERIE,CCO_NUMERO,
                                            CCO_TIPODOC  , CCO_FECHA   , CCO_CONCEPTO, CCO_MODULO     , CCO_NOCONTABLE,CCO_ESTADO,
                                            CCO_DESCUADRE, CCO_ADESTINO, CCO_CENTRO  , CCO_TIPO_CAMBIO,
                                            CCO_TRANSACC , CCO_ANULADO , CCO_BODEGA  , CCO_DIA        , CCO_MES,CCO_ANIO,CCO_IVA)         
                                    VALUES (PN_EMPRESA, v_codigo, v_periodo, V_SIGLA, PN_BOD_INI, V_NUMERO,
                                            PN_TIPODOC, TRUNC(V_FECHA), V_CONCEPTO, 7, 0, 2,
                                            0, 1, 0, 0, 6, 0, PN_BOD_INI, V_DIA, V_MES, v_periodo, 0);
                    
                                    --INSERTA CMOVINV
                                    INSERT INTO CMOVINV
                                           (CMO_EMPRESA, CMO_CCO_COMPROBA, CMO_TRANSACC, CMO_BODINI, CMO_BODFIN,
                                            CMO_EST_ENTREGA, CMO_VAL_CORREGIDA)
                                    VALUES (PN_EMPRESA, V_CODIGO, 6, PN_BOD_INI, PN_BOD_FIN,
                                            0, 0);
                                    --INSERTA EN TABLA TRANSFERENCIA
                                    INSERT INTO TRANSFERENCIA
                                      (TRF_EMPRESA, TRF_CODIGO, TRF_ESTADO)
                                    VALUES
                                      (PN_EMPRESA, V_CODIGO, 0);
                                    --INSET DMOVINV
                                    FOR I IN C_PRODUCTOS LOOP
                                      BEGIN
                                        INSERT INTO DMOVINV
                                               (DMO_EMPRESA    , DMO_CMO_COMPROBA, DMO_SECUENCIA  , DMO_PRODUCTO  , DMO_CATPRODUCTO,
                                                DMO_TRANSACCION, DMO_DEBCRE      , DMO_BODEGA     , DMO_CANTIDAD  , DMO_DEVUELTA,
                                                DMO_DESPACHADA , DMO_DEVDESPA    , DMO_COSTO      , DMO_TOTAL     , DMO_CANT_FISICA,
                                                DMO_CUENTA     , DMO_CENTRO      , DMO_TEMPERATURA, DMO_UDIGITADA , DMO_CDIGITADA,
                                                DMO_PDIGITADO  , DMO_CTAINV      , DMO_CCO_FECHA  , DMO_CCO_ESTADO, DMO_CCO_TIPODOC)
                                        VALUES (PN_EMPRESA     , V_CODIGO , V_SECUENCIA   , I.PRO_CODIGO                       , 0,
                                                6              , 2        , PN_BOD_INI    , ABS(I.SAL_CANTIDAD)                , 0,
                                                0              , 0        , I.SAL_TOTAL   , ((I.SAL_TOTAL)*ABS(I.SAL_CANTIDAD)), (I.SAL_CANTIDAD)*-1,
                                                V_CUENTA       , 0        , 15            , I.PRO_UNIDAD                       , ABS(I.SAL_CANTIDAD),
                                                0              , V_CTA_INV, TRUNC(V_FECHA), 1                                  , PN_TIPODOC);
                                        V_SECUENCIA := V_SECUENCIA + 1;
                                      /*EXCEPTION WHEN OTHERS THEN
                                         DBMS_OUTPUT.put_line('Error en producto '||i.producto||' '||i.id);*/
                                      END;
                                    END LOOP;
                    
                                    AST_FAC.ASIENTO_EGRESO_TRANSFERENCIA(PN_EMPRESA,V_CODIGO);
                    
                                    V_RESULTADO := v_codigo||' '||
                                                      ast_gen.numero_comprobante(pn_empresa,v_codigo);
                    
                                    :5 := V_RESULTADO;
                                  EXCEPTION
                                     WHEN OTHERS THEN
                                        RAISE;
                                  END;
                    """;
            try (CallableStatement cs = connection.prepareCall(sql)) {

                cs.setLong(1, empresa);
                cs.setBigDecimal(2, comprobante);
                cs.setLong(3, bodIni);
                cs.setLong(4, bodFin);

                cs.registerOutParameter(5, Types.VARCHAR); // OUT

                cs.execute();

                return cs.getString(5); //

            }
        });
    }
}
