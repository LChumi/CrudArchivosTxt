/*
 * Copyright (c) 2026 Luis Chumi.
 * Este software está licenciado bajo la Licencia Pública General de GNU versión 3. Puedes encontrar una copia de la licencia en https://www.gnu.org/licenses/gpl-3.0.html.
 *
 * Para consultas o comentarios, puedes contactarme en "luischumi.9@gmail.com".
 * Me gustaría ser reconocido por mi trabajo y estar abierto a colaboraciones o enseñanzas sobre el programa.
 */

package com.cumpleanos.erroresbodega.repository;

import com.cumpleanos.erroresbodega.models.dto.ValidarRecepcionResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ProcedureOracleRepository {

    private final EntityManager em;

    public ValidarRecepcionResult validarRecepcion(Long empresa, Long creposicion) {
        log.info("Validando recepcion de empresa: {} y creposicion: {}", empresa, creposicion);
        try {
            StoredProcedureQuery query = em.createStoredProcedureQuery("PRG_USR.AST_WEB.VALIDAR_RECEPCION");

            query.registerStoredProcedureParameter("PN_EMPRESA", Long.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("PN_CREPOSICION", Long.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("PN_ERROR", Integer.class, ParameterMode.OUT);
            query.registerStoredProcedureParameter("PN_RESPUESTA", String.class, ParameterMode.OUT);

            query.setParameter("PN_EMPRESA", empresa);
            query.setParameter("PN_CREPOSICION", creposicion);

            query.execute();

            // Obtener resultados
            Integer error = (Integer) query.getOutputParameterValue("PN_ERROR");
            String respuesta = (String) query.getOutputParameterValue("PN_RESPUESTA");
            log.info("Resultado de la validacion: Error: {}, Respuesta: {}", error, respuesta);
            return new ValidarRecepcionResult(error, respuesta);
        } catch (Exception e) {
            throw new RuntimeException("Error al ejecutar el procedimiento Validar Recepcion:");
        }
    }
}