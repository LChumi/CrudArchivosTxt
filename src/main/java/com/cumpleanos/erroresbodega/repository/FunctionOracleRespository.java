/*
 * Copyright (c) 2024 Luis Chumi.
 * Este software está licenciado bajo la Licencia Pública General de GNU versión 3. Puedes encontrar una copia de la licencia en https://www.gnu.org/licenses/gpl-3.0.html.
 *
 * Para consultas o comentarios, puedes contactarme en "luischumi.9@gmail.com".
 * Me gustaría ser reconocido por mi trabajo y estar abierto a colaboraciones o enseñanzas sobre el programa.
 */

package com.cumpleanos.erroresbodega.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
@Slf4j
public class FunctionOracleRespository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FunctionOracleRespository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }


    public double obtenerCostoTotalComprasEmpresas(Long empresa,
                                                   Date fechaInicio,
                                                   Date fechaFin,
                                                   String tipo_ing,
                                                   String tipo_sal,
                                                   Long producto){
        String sql = "SELECT PRG_USR.KP_PRODUCTO.TOT_COMPRAS_EMPRESAS(?, ?, ?, ?, ?, ?) FROM DUAL";

        log.info("parametros {}, {}, {}, {}, {}, {}",empresa,fechaInicio,fechaFin,tipo_ing,tipo_sal,producto);
        log.info("consulta {}",sql);

        return jdbcTemplate.queryForObject(sql,Double.class, empresa,fechaInicio,fechaFin,tipo_ing,tipo_sal,producto);
    }
}
