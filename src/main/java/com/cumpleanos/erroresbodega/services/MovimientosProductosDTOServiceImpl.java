/*
 * Copyright (c) 2024.
 *  Este código es propiedad de Luis Chumi y está protegido por las leyes de derechos de autor.
 *  Se concede el permiso para usar, copiar, modificar y distribuir este software con la condición de que se incluya este aviso en todas las copias o partes sustanciales del software.
 *  Para obtener ayuda, soporte o permisos adicionales, contacta a Luis Chumi en luischumi.9@gmail.com.
 */

package com.cumpleanos.erroresbodega.services;

import com.cumpleanos.erroresbodega.models.storage.MovimientosProductosDTO;
import com.cumpleanos.erroresbodega.models.storage.ProductoDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
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
public class MovimientosProductosDTOServiceImpl implements MovimientosProductosDTOService{

    @Value("${file.storafe.path.movimientos}")
    private String ruta;

    @Override
    public List<MovimientosProductosDTO> listar()throws IOException {
        List<MovimientosProductosDTO> listaMovimientos = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        try(Stream<Path> stream = Files.list(Paths.get(ruta))){
            stream.filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().startsWith("movimiento_"))
                    .forEach(path -> {
                        try {
                            List<String> contenidoArchivo = obtenerContenidoMoviminetos(path.getFileName().toString());
                            contenidoArchivo.forEach(linea ->{
                                try {
                                    MovimientosProductosDTO movimiento = objectMapper.readValue(linea, MovimientosProductosDTO.class);
                                    listaMovimientos.add(movimiento);
                                }catch (IOException e){
                                    throw new RuntimeException(e);
                                }
                            });
                        }catch (IOException e){
                            throw new RuntimeException(e);
                        }
                    });
        }
        Collections.sort(listaMovimientos);
        return listaMovimientos;
    }

    @Override
    public MovimientosProductosDTO guardar(MovimientosProductosDTO movimientosProductosDTO) throws IOException{
        String fechaFormateada = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        movimientosProductosDTO.setFecha(fechaFormateada);
        movimientosProductosDTO.setProductos(new ArrayList<>());
        movimientosProductosDTO.generarNuevoId();
        String nombreArchivo= String.format("movimiento_%s_%s.txt",movimientosProductosDTO.getId(),movimientosProductosDTO.getNombre());
        Path rutaArchivo = Paths.get(ruta,nombreArchivo);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonMovimeinto = objectMapper.writeValueAsString(movimientosProductosDTO);

        List<String> lineas = Collections.singletonList(jsonMovimeinto);
        Files.write(rutaArchivo,lineas);
        return movimientosProductosDTO;
    }

    @Override
    public ByteArrayInputStream exportarExcel() throws IOException {
        return null;
    }

    @Override
    public MovimientosProductosDTO editarMovimiento(MovimientosProductosDTO movimientosProductosDTO,ProductoDTO productoDTO) throws IOException {
        String nombreArchivo = String.format("movimiento_%s_%s.txt",movimientosProductosDTO.getId(),movimientosProductosDTO.getNombre());
        Path rutaArchivo = Paths.get(ruta,nombreArchivo);

        ObjectMapper objectMapper = new ObjectMapper();

        MovimientosProductosDTO movimientoExistente = objectMapper.readValue(Files.newBufferedReader(rutaArchivo), MovimientosProductosDTO.class);

        movimientoExistente.agregarProducto(productoDTO);

        objectMapper.writeValue(rutaArchivo.toFile(),movimientoExistente);

        return movimientoExistente;
    }

    private List<String> obtenerContenidoMoviminetos(String nombreArchivo) throws IOException{
        Path rutaArchivo = Paths.get(ruta,nombreArchivo);
        return Files.readAllLines(rutaArchivo);
    }
}
