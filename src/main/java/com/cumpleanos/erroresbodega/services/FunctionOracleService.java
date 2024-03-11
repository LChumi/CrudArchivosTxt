/*
 * Copyright (c) 2024.
 *  Este código es propiedad de Luis Chumi y está protegido por las leyes de derechos de autor.
 *  Se concede el permiso para usar, copiar, modificar y distribuir este software con la condición de que se incluya este aviso en todas las copias o partes sustanciales del software.
 *  Para obtener ayuda, soporte o permisos adicionales, contacta a Luis Chumi en luischumi.9@gmail.com.
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
