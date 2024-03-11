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
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ObservacionService {

    @Value("${file.storage.path.zhucay}")
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

    public List<Observacion> listarObservaciones() throws IOException {
        List<Observacion> listaObservaciones = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        try (Stream<Path> stream = Files.list(Paths.get(ruta))) {
            stream.filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().startsWith("observacion_"))
                    .forEach(path -> {
                        try {
                            List<String> contenidoArchivo = obtenerContenidoObservacion(path.getFileName().toString());
                            contenidoArchivo.forEach(linea -> {
                                try {
                                    Observacion observacion = objectMapper.readValue(linea, Observacion.class);
                                    listaObservaciones.add(observacion);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            });
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
        }

        Collections.sort(listaObservaciones);
        return listaObservaciones;
    }

    public List<String> obtenerContenidoObservacion(String nombreArchivo) throws IOException{
        Path rutaArchivo = Paths.get(ruta,nombreArchivo);
        return Files.readAllLines(rutaArchivo);
    }

    public ByteArrayInputStream exportarExcel() throws IOException{

        String[] columns = {"FECHA","ITEM","DESCRIPCION","UBICACION BULTO","UBICACION UNIDADES","CXB","STOCK","PRECIO","PRECIO TOTAL","RESPONSABLE RECLAMO","OBSERVACION","DIFERENCIA","RESPONSABLE SOLUCION","DETALLE", "FECHA SOLUCION"};

        Workbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        Sheet sheet= workbook.createSheet("Observaciones");
        Row row= sheet.createRow(0);

        for (int i =0; i< columns.length; i++){
            Cell cell = row.createCell(i);
            cell.setCellValue(columns[i]);
        }

        List<Observacion> observaciones=listarObservaciones();
        int initRow = 1; // Empieza en la fila 1 para dejar la fila 0 para los encabezados
        for (Observacion observacion:observaciones){
            row= sheet.createRow(initRow);
            row.createCell(0).setCellValue(observacion.getFecha());
            row.createCell(1).setCellValue(observacion.getItem());
            row.createCell(2).setCellValue(observacion.getDescripcion());
            row.createCell(3).setCellValue(observacion.getBulto());
            row.createCell(4).setCellValue(observacion.getUnidad());
            row.createCell(5).setCellValue(observacion.getCxb());
            row.createCell(6).setCellValue(observacion.getStock());
            row.createCell(7).setCellValue(observacion.getPrecio());
            row.createCell(8).setCellValue(observacion.getPrecioTotal());
            row.createCell(9).setCellValue(observacion.getUsuario());
            row.createCell(10).setCellValue(observacion.getDetalle());
            if (observacion.getDiferencia() != null){
                row.createCell(11).setCellValue(observacion.getDiferencia());
            }
            if (observacion.getCorreccion() != null) {
                row.createCell(12).setCellValue(observacion.getCorreccion().getUsuario());
                row.createCell(13).setCellValue(observacion.getCorreccion().getDetalle());
                row.createCell(14).setCellValue(observacion.getCorreccion().getFecha());
            }


            initRow++;
        }

        workbook.write(stream);
        workbook.close();
        return new ByteArrayInputStream(stream.toByteArray());
    }

}
