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

    public ByteArrayInputStream exportarExcel() throws IOException{

        String[] columns = {"FECHA","ITEM","DESCRIPCION","CXB","STOCK","PRECIO","PRECIO TOTAL","RESPONSABLE RECLAMO","OBSERVACION","RESPONSABLE SOLUCION","DETALLE", "FECHA SOLUCION"};

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
            row.createCell(3).setCellValue(observacion.getCxb());
            row.createCell(4).setCellValue(observacion.getStock());
            row.createCell(5).setCellValue(observacion.getPrecio());
            row.createCell(6).setCellValue(observacion.getPrecioTotal());
            row.createCell(7).setCellValue(observacion.getUsuario());
            row.createCell(8).setCellValue(observacion.getDetalle());
            if (observacion.getCorreccion() != null) {
                row.createCell(9).setCellValue(observacion.getCorreccion().getUsuario());
                row.createCell(10).setCellValue(observacion.getCorreccion().getDetalle());
                row.createCell(11).setCellValue(observacion.getCorreccion().getFecha());
            }

            initRow++;
        }

        workbook.write(stream);
        workbook.close();
        return new ByteArrayInputStream(stream.toByteArray());
    }

}
