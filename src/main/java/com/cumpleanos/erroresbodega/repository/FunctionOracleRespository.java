/*
 * Copyright (c) 2024.
 *  Este código es propiedad de Luis Chumi y está protegido por las leyes de derechos de autor.
 *  Se concede el permiso para usar, copiar, modificar y distribuir este software con la condición de que se incluya este aviso en todas las copias o partes sustanciales del software.
 *  Para obtener ayuda, soporte o permisos adicionales, contacta a Luis Chumi en luischumi.9@gmail.com.
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
