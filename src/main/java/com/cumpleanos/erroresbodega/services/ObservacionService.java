/*
 * Copyright (c) 2023-2024 Luis Chumi.
 * Este software está licenciado bajo la Licencia Pública General de GNU versión 3. Puedes encontrar una copia de la licencia en https://www.gnu.org/licenses/gpl-3.0.html.
 *
 * Para consultas o comentarios, puedes contactarme en "luischumi.9@gmail.com".
 * Me gustaría ser reconocido por mi trabajo y estar abierto a colaboraciones o enseñanzas sobre el programa.
 */

package com.cumpleanos.erroresbodega.services;

import com.cumpleanos.erroresbodega.config.RutasConfig;
import com.cumpleanos.erroresbodega.models.storage.Correccion;
import com.cumpleanos.erroresbodega.models.storage.Observacion;
import com.cumpleanos.erroresbodega.utils.ExcelFiller;
import com.cumpleanos.erroresbodega.utils.ObservacionesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;

@Service
public class ObservacionService {

    private final ObservacionesUtils observacionNarancay;
    private final ObservacionesUtils observacionZhucay;
    private final ObservacionesUtils observacionBodDanados;
    private final ObservacionesUtils observacionGColombia;
    private final ObservacionesUtils observacionGColombia2;
    private final ObservacionesUtils observacionesVergel;

    @Autowired
    public ObservacionService(RutasConfig rutas){
        observacionNarancay = new ObservacionesUtils(rutas.getRutaNarancay());
        observacionZhucay = new ObservacionesUtils(rutas.getRutaZhucay());
        observacionBodDanados = new ObservacionesUtils(rutas.getRutaBodDanados());
        observacionGColombia = new ObservacionesUtils(rutas.getRutaGcolombia());
        observacionGColombia2 = new ObservacionesUtils(rutas.getRutaGcolombia2());
        observacionesVergel = new ObservacionesUtils(rutas.getRutaVergel());
    }

    public Observacion guardarObservacionNarancay(Observacion observacion) throws IOException{
        return observacionNarancay.guardarObservacion(observacion);
    }
    public Observacion guardarObservacionZhucay(Observacion observacion) throws IOException{
        return observacionZhucay.guardarObservacion(observacion);
    }
    public Observacion guardarobservacionBodDanados(Observacion observacion) throws IOException{
        return observacionBodDanados.guardarObservacion(observacion);
    }
    public Observacion guardarObservacionGColombia(Observacion observacion) throws IOException{
        return observacionGColombia.guardarObservacion(observacion);
    }
    public Observacion guardarObservacionGColombia2(Observacion observacion) throws IOException{
        return observacionGColombia2.guardarObservacion(observacion);
    }
    public Observacion guardarObservacionVergel(Observacion observacion) throws IOException{
        return observacionesVergel.guardarObservacion(observacion);
    }

    public List<Observacion> listarNarancay() throws IOException {
        return observacionNarancay.listarObservaciones();
    }
    public List<Observacion> listarZhucay() throws IOException {
        return observacionZhucay.listarObservaciones();
    }
    public List<Observacion> listarBodDañados() throws IOException {
        return observacionBodDanados.listarObservaciones();
    }
    public List<Observacion> listarGColombia() throws IOException {
        return observacionGColombia.listarObservaciones();
    }
    public List<Observacion> listarGColombia2() throws IOException {
        return observacionGColombia2.listarObservaciones();
    }
    public List<Observacion> listarVergel() throws IOException {
        return observacionesVergel.listarObservaciones();
    }

    public Observacion editarObservacionNarancay(Observacion observacion, Correccion correccion) throws IOException{
        return observacionNarancay.editarObservacion(observacion, correccion);
    }
    public Observacion editarObservacionZhucay(Observacion observacion, Correccion correccion) throws IOException{
        return observacionZhucay.editarObservacion(observacion, correccion);
    }
    public Observacion editarObservacionBodDa(Observacion observacion, Correccion correccion) throws IOException{
        return observacionBodDanados.editarObservacion(observacion, correccion);
    }
    public Observacion editarObservacionGColombia(Observacion observacion, Correccion correccion) throws IOException {
        return observacionGColombia.editarObservacion(observacion, correccion);
    }
    public Observacion editarObservacionGColombia2(Observacion observacion, Correccion correccion) throws IOException {
        return observacionGColombia2.editarObservacion(observacion, correccion);
    }
    public Observacion editarObservacionVergel(Observacion observacion, Correccion correccion) throws IOException {
        return observacionesVergel.editarObservacion(observacion,correccion);
    }

    public ByteArrayInputStream exportarExcelZhucay() throws IOException{

        String[] columns = {"FECHA", "RESPONSABLE" , "ITEM" , "STOCK", "FISICO" , "DIFERENCIA" , "RESOPONSABLE SOLUCION" , "DETALLE" , "FECHA SOLUCION" , "UBICACION BULTO" , "UBICACION UNIDADES"};

        List<Observacion> observacionesZhucay = listarZhucay();

        ExcelFiller excelFiller = (row,observacion ) -> {
            row.createCell(0).setCellValue(observacion.getFecha());
            row.createCell(1).setCellValue(observacion.getUsuario());
            row.createCell(2).setCellValue(observacion.getItem());
            row.createCell(3).setCellValue(observacion.getDescripcion());
            row.createCell(4).setCellValue(observacion.getStock());
            row.createCell(5).setCellValue(observacion.getDetalle());
            if (observacion.getDiferencia() != null){
                row.createCell(6).setCellValue(observacion.getDiferencia());
            }
            if (observacion.getCorreccion() != null) {
                row.createCell(7).setCellValue(observacion.getCorreccion().getUsuario());
                row.createCell(8).setCellValue(observacion.getCorreccion().getDetalle());
                row.createCell(9).setCellValue(observacion.getCorreccion().getFecha());
            }
            row.createCell(10).setCellValue(observacion.getBulto());
            row.createCell(11).setCellValue(observacion.getUnidad());
        };

        return observacionZhucay.exportarExcel(observacionesZhucay,columns,excelFiller);
    }
    public ByteArrayInputStream exportarExcelNarancay() throws IOException{
        String[] columns = {"FECHA","ITEM","DESCRIPCION","CXB","STOCK","PRECIO","PRECIO TOTAL","RESPONSABLE RECLAMO","OBSERVACION","RESPONSABLE SOLUCION","DETALLE", "FECHA SOLUCION"};

        List<Observacion> observacionesNarancay = listarNarancay();

        ExcelFiller excelFiller = (row,observacion) ->{
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
        };
        return observacionNarancay.exportarExcel(observacionesNarancay,columns,excelFiller);
    }
    public ByteArrayInputStream exportarExcelBodDa() throws IOException {
        List<Observacion> observacionesNarancay = listarBodDañados();
        return exportarExcel(observacionesNarancay, observacionBodDanados);
    }
    public ByteArrayInputStream exportarExcelGColombia() throws IOException {
        List<Observacion> observacionesNarancay = listarGColombia();
        return exportarExcel(observacionesNarancay, observacionGColombia);
    }
    public ByteArrayInputStream exportarExcelGColombia2() throws IOException {
        List<Observacion> observacionesNarancay = listarGColombia2();
        return exportarExcel(observacionesNarancay, observacionGColombia);
    }
    public ByteArrayInputStream exportarExcelVergel() throws IOException {
        List<Observacion> observacionesNarancay = listarVergel();
        return exportarExcel(observacionesNarancay, observacionesVergel);
    }


    private ByteArrayInputStream exportarExcel(List<Observacion> observaciones, ObservacionesUtils observacionExporter) throws IOException {
        String[] columns = {"FECHA", "ITEM", "DESCRIPCION", "CANTIDAD DAÑADOS", "OBSERVACION", "RESPONSABLE", "RESPONSABLE SOLUCION", "DETALLE", "FECHA SOLUCION"};

        ExcelFiller excelFiller = (row, observacion) -> {
            row.createCell(0).setCellValue(observacion.getFecha());
            row.createCell(1).setCellValue(observacion.getItem());
            row.createCell(2).setCellValue(observacion.getDescripcion());
            row.createCell(3).setCellValue(observacion.getDiferencia());
            row.createCell(4).setCellValue(observacion.getDetalle());
            row.createCell(5).setCellValue(observacion.getUsuario());
            if (observacion.getCorreccion() != null) {
                row.createCell(6).setCellValue(observacion.getCorreccion().getUsuario());
                row.createCell(7).setCellValue(observacion.getCorreccion().getDetalle());
                row.createCell(8).setCellValue(observacion.getCorreccion().getFecha());
            }
        };
        return observacionExporter.exportarExcel(observaciones, columns, excelFiller);
    }

}
