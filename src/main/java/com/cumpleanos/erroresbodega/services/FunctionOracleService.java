/*
 * Copyright (c) 2024 Luis Chumi.
 * Este software está licenciado bajo la Licencia Pública General de GNU versión 3. Puedes encontrar una copia de la licencia en https://www.gnu.org/licenses/gpl-3.0.html.
 *
 * Para consultas o comentarios, puedes contactarme en "luischumi.9@gmail.com".
 * Me gustaría ser reconocido por mi trabajo y estar abierto a colaboraciones o enseñanzas sobre el programa.
 */

package com.cumpleanos.erroresbodega.services;

import com.cumpleanos.erroresbodega.repository.FunctionOracleRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class FunctionOracleService {

    private final FunctionOracleRespository respository;
    private static final Date FECHA_INICIAL = java.sql.Date.valueOf("2016-09-01");

    @Autowired
    public FunctionOracleService(FunctionOracleRespository respository){
        this.respository= respository;
    }

    public double obtenerCostoTotalComprasEmpresas(Long empresa, Long producto) {
        String tipoIng = "c";
        String tipoSal = "u";
        Date fechaFin = new Date();
        return respository.obtenerCostoTotalComprasEmpresas(empresa,FECHA_INICIAL, fechaFin, tipoIng, tipoSal, producto);
    }
}
