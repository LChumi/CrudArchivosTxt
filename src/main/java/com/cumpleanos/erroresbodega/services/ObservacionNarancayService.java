/*
 * Copyright (c) 2024.
 *  Este código es propiedad de Luis Chumi y está protegido por las leyes de derechos de autor.
 *  Se concede el permiso para usar, copiar, modificar y distribuir este software con la condición de que se incluya este aviso en todas las copias o partes sustanciales del software.
 *  Para obtener ayuda, soporte o permisos adicionales, contacta a Luis Chumi en luischumi.9@gmail.com.
 */

package com.cumpleanos.erroresbodega.services;

import com.cumpleanos.erroresbodega.models.storage.Correccion;
import com.cumpleanos.erroresbodega.models.storage.Observacion;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ObservacionNarancayService {

    @Value("${file.storage.path.narancay}")
    private String ruta;

    public Observacion guardarObservacion(Observacion observacion) throws IOException {
        String fechaFormateada = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        String nombreArchivo = String.format("observacion_%s_%s_%s.txt",fechaFormateada,observacion.getUsuario(),observacion.getItem());
        Path rutaArchivo = Paths.get(ruta,nombreArchivo);
        observacion.setFecha(fechaFormateada);
        observacion.calcularPrecioTotal();

        ObjectMapper objectMapper=new ObjectMapper();
        String jsonObservacion= objectMapper.writeValueAsString(observacion);

        List<String> lineas = Collections.singletonList(jsonObservacion);
        Files.write(rutaArchivo,lineas);
        return observacion;
    }

    public List<String> listarObservaciones() throws IOException{
        try(Stream<Path> stream = Files.list(Paths.get(ruta))){
            return stream
                    .filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().startsWith("observacion_"))
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .collect(Collectors.toList());
        }
    }

    public Observacion editarObservacion(Observacion observacion, Correccion correccion) throws IOException{
        String fechaFormateada = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        correccion.setFecha(fechaFormateada);
        String nombreArchivo=String.format("observacion_%s_%s_%s.txt",observacion.getFecha(),observacion.getUsuario(),observacion.getItem());
        Path rutaArchivo = Paths.get(ruta, nombreArchivo);

        ObjectMapper objectMapper = new ObjectMapper();

        Observacion observacionExisitente =objectMapper.readValue(Files.newBufferedReader(rutaArchivo), Observacion.class);

        observacionExisitente.setCorreccion(correccion);

        objectMapper.writeValue(rutaArchivo.toFile(),observacionExisitente);

        return observacionExisitente;
    }

    public List<String> obtenerContenidoObservacion(String nombreArchivo) throws IOException{
        Path rutaArchivo = Paths.get(ruta,nombreArchivo);
        return Files.readAllLines(rutaArchivo);
    }

}
