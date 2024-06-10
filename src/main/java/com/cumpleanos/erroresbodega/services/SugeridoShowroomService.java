/*
 * Copyright (c) 2024 Luis Chumi.
 * Este software está licenciado bajo la Licencia Pública General de GNU versión 3. Puedes encontrar una copia de la licencia en https://www.gnu.org/licenses/gpl-3.0.html.
 *
 * Para consultas o comentarios, puedes contactarme en "luischumi.9@gmail.com".
 * Me gustaría ser reconocido por mi trabajo y estar abierto a colaboraciones o enseñanzas sobre el programa.
 */

package com.cumpleanos.erroresbodega.services;

import com.cumpleanos.erroresbodega.models.storage.ProductoShowroom;
import com.cumpleanos.erroresbodega.models.storage.SugeridoShowroom;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
public class SugeridoShowroomService {

    @Value("${file.storage.path.sugeridos}")
    private String ruta;

    public List<SugeridoShowroom> listar() throws IOException{
        List<SugeridoShowroom> listaSugeridos = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        
        try(Stream<Path> stream = Files.list(Paths.get(ruta))) {
            stream.filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().startsWith("sugerido_"))
                    .forEach(path -> {
                        try {
                            List<String> contenidoArchivo = obtenerContenidoSugeridos(path.getFileName().toString());
                            contenidoArchivo.forEach(linea ->{
                                try {
                                    SugeridoShowroom sugerido = objectMapper.readValue(linea, SugeridoShowroom.class);
                                    listaSugeridos.add(sugerido);
                                }catch (IOException e){
                                    throw new RuntimeException(e);
                                }
                            });
                        }catch (IOException e){
                            throw new RuntimeException(e);
                        }
                    });
        }
        Collections.sort(listaSugeridos);
        return listaSugeridos;
    }
    
    public SugeridoShowroom guardarSugerido(SugeridoShowroom sugerido) throws IOException {
        String fecha= new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        sugerido.setFecha(fecha);
        sugerido.setProductoShowrooms(new ArrayList<>());
        sugerido.generarNuevoId();
        
        String nombreArchivo = String.format("sugerido_%s_%s.json",sugerido.getId(),sugerido.getDetalle());
        Path rutaArchivo = Paths.get(ruta,nombreArchivo);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonSugerido = objectMapper.writeValueAsString(sugerido);

        List<String> lineas = Collections.singletonList(jsonSugerido);
        Files.write(rutaArchivo,lineas);
        return sugerido;
    }
    
    public SugeridoShowroom editarSugerido(Long id, String detalle , ProductoShowroom producto) throws IOException{
        String nombreArchivo = String.format("sugerido_%s_%s.json",id,detalle);
        Path rutaArchivo = Paths.get(ruta,nombreArchivo);
        
        ObjectMapper objectMapper = new ObjectMapper();
        
        SugeridoShowroom sugeridoExistente = objectMapper.readValue(Files.newBufferedReader(rutaArchivo), SugeridoShowroom.class);
        
        sugeridoExistente.agregarProducto(producto);
        
        objectMapper.writeValue(rutaArchivo.toFile(),sugeridoExistente);
        
        return sugeridoExistente;
    }
    
    public SugeridoShowroom editarSUgeridoEliminar(Long id, String detalle, ProductoShowroom producto) throws IOException{
        String nombreArchivo = String.format("sugerido_%s_%s.json",id,detalle);
        Path rutaArchivo = Paths.get(ruta,nombreArchivo);

        ObjectMapper objectMapper = new ObjectMapper();
        SugeridoShowroom sugeridoExistente = objectMapper.readValue(Files.newBufferedReader(rutaArchivo), SugeridoShowroom.class);

        sugeridoExistente.eliminarProducto(producto);

        objectMapper.writeValue(rutaArchivo.toFile(),sugeridoExistente);

        return sugeridoExistente;
    }
    
    public SugeridoShowroom getSugerido(Long id,String detalle) throws IOException{
        String nombreArchivo = String.format("sugerido_%s_%s.json",id ,detalle);
        Path rutaArchivo = Paths.get(ruta,nombreArchivo);
        ObjectMapper objectMapper = new ObjectMapper();
        
        return objectMapper.readValue(Files.newBufferedReader(rutaArchivo), SugeridoShowroom.class);
    }
    
    private List<String> obtenerContenidoSugeridos(String nombreArchivo) throws IOException{
        Path rutaArchivo = Paths.get(ruta,nombreArchivo);
        return Files.readAllLines(rutaArchivo);
    }

    public ByteArrayInputStream exportarExcel(SugeridoShowroom sugerido) throws  IOException{
        Workbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        Sheet sheet = workbook.createSheet("Sugerido");

        Row headerRow = sheet.createRow(0);
        Cell nombreCell = headerRow.createCell(0);
        nombreCell.setCellValue("Detalle");
        Cell idCell = headerRow.createCell(1);
        idCell.setCellValue("Fecha");
        Cell usuarioCell = headerRow.createCell(2);
        usuarioCell.setCellValue("Usuario");

        String nombreSugerido = sugerido.getDetalle();
        String fecha = sugerido.getFecha();
        String usuario = sugerido.getUsuario();

        Row movimientoRow = sheet.createRow(1);
        movimientoRow.createCell(0).setCellValue(nombreSugerido);
        movimientoRow.createCell(1).setCellValue(fecha);
        movimientoRow.createCell(2).setCellValue(usuario);

        Row productosHeaderRow = sheet.createRow(3);
        productosHeaderRow.createCell(0).setCellValue("Barra");
        productosHeaderRow.createCell(1).setCellValue("Item");
        productosHeaderRow.createCell(2).setCellValue("Descripcion");
        productosHeaderRow.createCell(3).setCellValue("StockNarancay");
        productosHeaderRow.createCell(4).setCellValue("StockZhucay");
        productosHeaderRow.createCell(5).setCellValue("StrockShowroom");
        productosHeaderRow.createCell(6).setCellValue("Cantidad");
        productosHeaderRow.createCell(7).setCellValue("Observacion");

        List<ProductoShowroom> productos = sugerido.getProductoShowrooms();

        int rowNum = 4; // Empezamos en la fila 3 después de los encabezados
        for (ProductoShowroom producto : productos) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(producto.getBarra());
            row.createCell(1).setCellValue(producto.getItem());
            row.createCell(2).setCellValue(producto.getDescripcion());
            row.createCell(3).setCellValue(producto.getStockNc());
            row.createCell(4).setCellValue(producto.getStockZh());
            row.createCell(5).setCellValue(producto.getStockSh());
            row.createCell(6).setCellValue(producto.getCantidad());
            row.createCell(7).setCellValue(producto.getObservacion());
        }

        workbook.write(stream);
        workbook.close();

        return new ByteArrayInputStream(stream.toByteArray());

    }

}
