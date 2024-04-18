/*
 * Copyright (c) 2024 Luis Chumi.
 * Este software está licenciado bajo la Licencia Pública General de GNU versión 3. Puedes encontrar una copia de la licencia en https://www.gnu.org/licenses/gpl-3.0.html.
 *
 * Para consultas o comentarios, puedes contactarme en "luischumi.9@gmail.com".
 * Me gustaría ser reconocido por mi trabajo y estar abierto a colaboraciones o enseñanzas sobre el programa.
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

    @Value("${file.storage.path.movimientos.narancay}")
    private String rutaMovimientosNarancay;

    @Value("${file.storage.path.movimientos.zhucay}")
    private String rutaMovimientosZhucay;

    @Value("${file.storage.path.gcolombia}")
    private String rutaGcolombia;

    @Value("${file.storage.path.vergel}")
    private String rutaVergel;
}
