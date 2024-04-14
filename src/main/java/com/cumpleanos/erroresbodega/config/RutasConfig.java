/*
 * Copyright (c) 2024.
 *  Este código es propiedad de Luis Chumi y está protegido por las leyes de derechos de autor.
 *  Se concede el permiso para usar, copiar, modificar y distribuir este software con la condición de que se incluya este aviso en todas las copias o partes sustanciales del software.
 *  Para obtener ayuda, soporte o permisos adicionales, contacta a Luis Chumi en luischumi.9@gmail.com.
 */

package com.cumpleanos.erroresbodega.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class RutasConfig {

    @Value("${file.storage.path.zhucay}")
    private String rutaZhucay;

    @Value("${file.storage.path.narancay}")
    private String rutaNarancay;

    @Value("${file.storage.path.danados}")
    private String rutaBodDanados;

    @Value("${file.storage.path.calificaciones}")
    private String rutaCalificaciones;

    @Value("${file.storage.path.imagenes}")
    private String rutaImagenes;

    @Value("${file.storafe.path.movimientos.narancay}")
    private String rutaMovimientosNarancay;

    @Value("${file.storafe.path.movimientos.zhucay}")
    private String rutaMovimientosZhucay;
}
