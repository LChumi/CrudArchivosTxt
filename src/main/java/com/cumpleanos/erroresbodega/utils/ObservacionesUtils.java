/*
 * Copyright (c) 2024 Luis Chumi.
 * Este software está licenciado bajo la Licencia Pública General de GNU versión 3. Puedes encontrar una copia de la licencia en https://www.gnu.org/licenses/gpl-3.0.html.
 *
 * Para consultas o comentarios, puedes contactarme en "luischumi.9@gmail.com".
 * Me gustaría ser reconocido por mi trabajo y estar abierto a colaboraciones o enseñanzas sobre el programa.
 */

package com.cumpleanos.erroresbodega.utils;

import com.cumpleanos.erroresbodega.models.storage.Correccion;
import com.cumpleanos.erroresbodega.models.storage.Observacion;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
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

public class ObservacionesUtils {

    private String ruta;

    public ObservacionesUtils(String ruta){
        this.ruta=ruta;
    }

    public Observacion guardarObservacion(Observacion observacion) throws IOException {
        String fechaFormateada = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        observacion.generarNuevoId();
        String nombreArchivo = String.format("observacion_%s_%s_%s.txt",fechaFormateada,observacion.getUsuario(),observacion.getId());
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
            ObjectMapper objectMapper = new ObjectMapper();

            String nombreArchivoId=String.format("observacion_%s_%s_%s.txt",observacion.getFecha(),observacion.getUsuario(),observacion.getId());
            Path rutaArchivoId = Paths.get(ruta, nombreArchivoId);

            if (Files.exists(rutaArchivoId)) {
                Observacion observacionExisitente =objectMapper.readValue(Files.newBufferedReader(rutaArchivoId), Observacion.class);
                observacionExisitente.setCorreccion(correccion);
                objectMapper.writeValue(rutaArchivoId.toFile(),observacionExisitente);
                return observacionExisitente;
            }else {
                String nombreArchivoItem = String.format("observacion_%s_%s_%s.txt", observacion.getFecha(), observacion.getUsuario(), observacion.getItem());
                Path rutaArchivoItem = Paths.get(ruta, nombreArchivoItem);
                if (Files.exists(rutaArchivoItem)) {
                    Observacion observacionExistente = objectMapper.readValue(Files.newBufferedReader(rutaArchivoItem), Observacion.class);
                    observacionExistente.setCorreccion(correccion);
                    objectMapper.writeValue(rutaArchivoItem.toFile(), observacionExistente);
                    return observacionExistente;
                } else {
                    // Si el archivo con el nombre generado por 'item' tampoco se encuentra, puedes manejar esta situación según tus necesidades
                    throw new FileNotFoundException("No se encontró el archivo de observación para la observación especificada.");
                }
            }
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

    private List<String> obtenerContenidoObservacion(String nombreArchivo) throws IOException{
        Path rutaArchivo = Paths.get(ruta,nombreArchivo);
        return Files.readAllLines(rutaArchivo);
    }

    public ByteArrayInputStream exportarExcel(List<Observacion> observaciones, String[] columns ,ExcelFiller excelFiller)throws IOException{
        Workbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        Sheet sheet = workbook.createSheet("Observaciones");
        Row row = sheet.createRow(0);

        for (int i = 0 ; i< columns.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(columns[i]);
        }

        int initRow = 1;
        for (Observacion observacion : observaciones){
            row = sheet.createRow(initRow);
            excelFiller.fillRow(row, observacion);
            initRow++;
        }
        workbook.write(stream);
        workbook.close();
        return new ByteArrayInputStream(stream.toByteArray());
    }

}
