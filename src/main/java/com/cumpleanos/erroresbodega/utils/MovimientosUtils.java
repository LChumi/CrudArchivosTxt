/*
 * Copyright (c) 2024 Luis Chumi.
 * Este software está licenciado bajo la Licencia Pública General de GNU versión 3.
 * Puedes encontrar una copia de la licencia en https://www.gnu.org/licenses/gpl-3.0.html.
 *
 * Para consultas o comentarios, puedes contactarme en luischumi.9@gmail.com
 */

package com.cumpleanos.erroresbodega.utils;

import com.cumpleanos.erroresbodega.models.storage.MovimientosProductosDTO;
import com.cumpleanos.erroresbodega.models.storage.ProductoDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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

public class MovimientosUtils {

    private String ruta;

    public MovimientosUtils(String ruta) {this.ruta = ruta;}

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

    public MovimientosProductosDTO editarMovimiento(MovimientosProductosDTO movimientosProductosDTO, ProductoDTO productoDTO) throws IOException {
        String nombreArchivo = String.format("movimiento_%s_%s.txt",movimientosProductosDTO.getId(),movimientosProductosDTO.getNombre());
        Path rutaArchivo = Paths.get(ruta,nombreArchivo);

        ObjectMapper objectMapper = new ObjectMapper();

        MovimientosProductosDTO movimientoExistente = objectMapper.readValue(Files.newBufferedReader(rutaArchivo), MovimientosProductosDTO.class);

        movimientoExistente.agregarProducto(productoDTO);

        objectMapper.writeValue(rutaArchivo.toFile(),movimientoExistente);

        return movimientoExistente;
    }

    private List<String> obtenerContenidoMoviminetos(String nombreArchivo) throws IOException {
        Path rutaArchivo = Paths.get(ruta,nombreArchivo);
        return Files.readAllLines(rutaArchivo);
    }

    public ByteArrayInputStream exportarExcelAll() throws IOException {
        String[] columns ={"FECHA", "NOMBRE"};

        Workbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        Sheet sheet= workbook.createSheet("Movimientos");
        Row row = sheet.createRow(0);

        for (int i=0; i<columns.length;i++){
            Cell cell = row.createCell(i);
            cell.setCellValue(columns[i]);
        }

        List<MovimientosProductosDTO> movimientosProductosDTOS=listar();
        int initRow =1;
        for (MovimientosProductosDTO movimineto : movimientosProductosDTOS){
            row= sheet.createRow(initRow);
            row.createCell(0).setCellValue(movimineto.getId());
            row.createCell(1).setCellValue(movimineto.getNombre());
            initRow++;
        }

        workbook.write(stream);
        workbook.close();

        return new ByteArrayInputStream(stream.toByteArray());
    }

    public ByteArrayInputStream exportarExcel(MovimientosProductosDTO movimiento) throws IOException {
        // Crear un nuevo libro de trabajo Excel
        Workbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        // Crear una nueva hoja en el libro de trabajo
        Sheet sheet = workbook.createSheet("Movimiento");

        // Crear una fila para los encabezados personalizados (nombre y ID)
        Row headerRow = sheet.createRow(0);
        Cell nombreCell = headerRow.createCell(1);
        nombreCell.setCellValue("Nombre");
        Cell idCell = headerRow.createCell(2);
        idCell.setCellValue("Fecha");
        Cell usuarioCell = headerRow.createCell(3);
        usuarioCell.setCellValue("Usuario");

        // Obtener el nombre y el ID del movimiento especificado
        String nombreMovimiento = movimiento.getNombre();
        String fecha = movimiento.getFecha();
        String usuario = movimiento.getUsuario();

        Row movimientoRow = sheet.createRow(1);
        movimientoRow.createCell(1).setCellValue(nombreMovimiento);
        movimientoRow.createCell(2).setCellValue(fecha);
        movimientoRow.createCell(3).setCellValue(usuario);

        // Crear una fila para los encabezados de los productos
        Row productosHeaderRow = sheet.createRow(2);
        productosHeaderRow.createCell(0).setCellValue("Barra");
        productosHeaderRow.createCell(1).setCellValue("Item");
        productosHeaderRow.createCell(2).setCellValue("Detalle");
        productosHeaderRow.createCell(3).setCellValue("Cantidad");

        // Obtener los productos del movimiento especificado
        List<ProductoDTO> productos = movimiento.getProductos();

        // Iterar sobre cada producto y escribir sus datos en el archivo Excel
        int rowNum = 3; // Empezamos en la fila 3 después de los encabezados
        for (ProductoDTO producto : productos) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(producto.getBarra());
            row.createCell(1).setCellValue(producto.getItem());
            row.createCell(2).setCellValue(producto.getDetalle());
            row.createCell(3).setCellValue(producto.getCantidad());
        }

        // Escribir el libro de trabajo Excel en el flujo de salida
        workbook.write(stream);
        workbook.close();

        // Devolver un flujo de entrada con los datos del archivo Excel
        return new ByteArrayInputStream(stream.toByteArray());
    }


}
