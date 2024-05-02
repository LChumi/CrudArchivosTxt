/*
 * Copyright (c) 2024 Luis Chumi.
 * Este software está licenciado bajo la Licencia Pública General de GNU versión 3. Puedes encontrar una copia de la licencia en https://www.gnu.org/licenses/gpl-3.0.html.
 *
 * Para consultas o comentarios, puedes contactarme en "luischumi.9@gmail.com".
 * Me gustaría ser reconocido por mi trabajo y estar abierto a colaboraciones o enseñanzas sobre el programa.
 */

package com.cumpleanos.erroresbodega.services;

import com.cumpleanos.erroresbodega.models.storage.Calificacion;
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
public class CalificacionService {

    @Value("${file.storage.path.calificaciones}")
    private String ruta;

    public Calificacion guardarCalificacion(Calificacion calificacion) throws IOException {
        String fechaFormateada = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        calificacion.generarNuevoId();
        String nombreArchivo = String.format("calificacion_%s_%s.txt", calificacion.getId(), calificacion.getEmpleado());
        Path rutaArchivo = Paths.get(ruta, nombreArchivo);
        calificacion.setFecha(fechaFormateada);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonCalificacion = objectMapper.writeValueAsString(calificacion);

        List<String> lineas = Collections.singletonList(jsonCalificacion);
        Files.write(rutaArchivo, lineas);

        return calificacion;
    }

    public List<Calificacion> listarCalificaciones() throws IOException {
        List<Calificacion> listaCalificaciones = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        try (Stream<Path> stream = Files.list(Paths.get(ruta))) {
            stream.filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().startsWith("calificacion_"))
                    .forEach(path -> {
                        try {
                            List<String> contenidoArchivo = obtenerContenidoCalificacion(path.getFileName().toString());
                            contenidoArchivo.forEach(linea -> {
                                try {
                                    Calificacion calificacion = objectMapper.readValue(linea, Calificacion.class);
                                    listaCalificaciones.add(calificacion);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            });
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
        }
        Collections.sort(listaCalificaciones);
        return listaCalificaciones;
    }


    private List<String> obtenerContenidoCalificacion(String nombreArchivo) throws  IOException{
        Path rutaArchivo = Paths.get(ruta,nombreArchivo);
        return Files.readAllLines(rutaArchivo);
    }

    public ByteArrayInputStream exportarExcel() throws IOException{
        String[] columns = {"FECHA","HORA", "EMPLEADO", "CALIFICACION" ,"CLIENTE", "OBSERVACION", "ACEPTA POLITICAS"};

        Workbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        Sheet sheet= workbook.createSheet("Calificaciones");
        Row row = sheet.createRow(0);

        for (int i=0; i<columns.length; i++){
            Cell cell = row.createCell(i);
            cell.setCellValue(columns[i]);
        }

        List<Calificacion> calificaciones = listarCalificaciones();
        int initRow = 1;
        for(Calificacion calificacion:calificaciones){
            row= sheet.createRow(initRow);
            row.createCell(0).setCellValue(calificacion.getFecha());
            row.createCell(1).setCellValue(calificacion.getHora());
            row.createCell(2).setCellValue(calificacion.getEmpleado());
            row.createCell(3).setCellValue(calificacion.getCalificacionEnum().toString());
            row.createCell(4).setCellValue(calificacion.getCliente());
            row.createCell(5).setCellValue(calificacion.getObservacion());
            row.createCell(6).setCellValue(calificacion.isAceptaPoliticas());

            initRow++;
        }

        workbook.write(stream);
        workbook.close();
        return new ByteArrayInputStream(stream.toByteArray());
    }
}
