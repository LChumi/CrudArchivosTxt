/*
 * Copyright (c) 2023.
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
import java.io.UncheckedIOException;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ObservacionService {

    @Value("${file.storage.path}")
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

    public List<String> getObservacionByUsuario(String fecha,String idUsuario,String idProducto)throws IOException {
        String nombreArchivo = String.format("observacion_%s_%s_%s.txt",fecha,idUsuario,idProducto);
        Path rutaArchivo = Paths.get(ruta,nombreArchivo);
        return Files.readAllLines(rutaArchivo);
    }

    public List<String> getByProducto(String idProducto) throws IOException {
        try (Stream<Path> stream = Files.walk(Paths.get(ruta))) {
            return stream
                    .filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().contains(idProducto + ".txt"))
                    .flatMap(path -> {
                        try {
                            return Files.readAllLines(path).stream();
                        } catch (IOException e) {
                            throw new UncheckedIOException(e);
                        }
                    })
                    .collect(Collectors.toList());
        }
    }

    public List<Observacion> guardarObservaciones(List<Observacion> observaciones) throws IOException {

        String usuario=observaciones.get(1).getUsuario();
        String fechaFormateada = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

        String nombreArchivo= String.format("observacion_%s_%s.txt",fechaFormateada,usuario);
        Path rutaArchivo =Paths.get(ruta,nombreArchivo);

        List<String> lineas= new ArrayList<>();
        for(Observacion observacion:observaciones){
            lineas.add(observacion.toString());
        }

        Files.write(rutaArchivo,lineas, StandardOpenOption.CREATE,StandardOpenOption.APPEND);

        return observaciones;
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

    public List<String> obtenerContenidoObservacion(String nombreArchivo) throws IOException{
        Path rutaArchivo = Paths.get(ruta,nombreArchivo);
        return Files.readAllLines(rutaArchivo);
    }



}
