/*
 * Copyright (c) 2024.
 *  Este código es propiedad de Luis Chumi y está protegido por las leyes de derechos de autor.
 *  Se concede el permiso para usar, copiar, modificar y distribuir este software con la condición de que se incluya este aviso en todas las copias o partes sustanciales del software.
 *  Para obtener ayuda, soporte o permisos adicionales, contacta a Luis Chumi en luischumi.9@gmail.com.
 */

package com.cumpleanos.erroresbodega.services;

import com.cumpleanos.erroresbodega.models.storage.Calificacion;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@Service
public class CalificacionService {

    @Value("${file.storage.path.calificaciones}")
    private String ruta;

    public Calificacion guardarCalificacion(Calificacion calificacion) throws IOException{
        String fechaFormateada = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        String nombreArchivo = String.format("calificacion_%s_%s_%s.txt",fechaFormateada,calificacion.getId(),calificacion.getEmpleado());
        Path rutaArchivo = Paths.get(ruta,nombreArchivo);
        calificacion.setFecha(fechaFormateada);
        calificacion.generarNuevoId();

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonCalificacion = objectMapper.writeValueAsString(calificacion);

        List<String> lineas = Collections.singletonList(jsonCalificacion);
        Files.write(rutaArchivo,lineas);

        return calificacion;
    }

    public List<Calificacion> listarCalificaciones() throws IOException{
        List<Calificacion> listaCalificaciones = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        try (Stream<Path> stream = Files.list(Paths.get(ruta))){
            stream.filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().startsWith("calificacion_"))
                    .forEach(path -> {
                        try {
                            List<String> contenidoArchivo = obtenerContenidoCalificacion(path.getFileName().toString());
                            contenidoArchivo.forEach(linea -> {
                                try {
                                    Calificacion calificacion = objectMapper.readValue(linea, Calificacion.class);
                                    listaCalificaciones.add(calificacion);
                                }catch(IOException e) {
                                    throw new RuntimeException(e);
                                }
                            });
                        }catch(IOException e){
                            throw new RuntimeException(e);
                        }
                    });
        }
        Collections.sort(listaCalificaciones);
        return listaCalificaciones;
    }

    public List<String> obtenerContenidoCalificacion(String nombreArchivo) throws  IOException{
        Path rutaArchivo = Paths.get(ruta,nombreArchivo);
        return Files.readAllLines(rutaArchivo);
    }
}
