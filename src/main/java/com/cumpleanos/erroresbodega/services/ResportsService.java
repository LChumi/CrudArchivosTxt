/*
 * Copyright (c) 2024 Luis Chumi.
 * Este software está licenciado bajo la Licencia Pública General de GNU versión 3. Puedes encontrar una copia de la licencia en https://www.gnu.org/licenses/gpl-3.0.html.
 *
 * Para consultas o comentarios, puedes contactarme en "luischumi.9@gmail.com".
 * Me gustaría ser reconocido por mi trabajo y estar abierto a colaboraciones o enseñanzas sobre el programa.
 */

package com.cumpleanos.erroresbodega.services;

import com.cumpleanos.erroresbodega.models.Items;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ResportsService {

    @Autowired
    private DataSource dataSource;

    public byte[] getReport(String cco, String format) throws Exception {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("P_CODIGO", cco);
        return generateReport("reports/pedidos.jrxml",parameters, format);
    }

    public byte[] getReportByImages(Items items, String format) throws Exception {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("P_PRODUCTOS", items.ids());

        return generateReport("reports/productosImages.jrxml", parameters, format);
    }

    public byte[] getReportClientePedido(String pedido, String format) throws Exception {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("P_CPEDIDO", pedido);
        return generateReport("reports/pedidosCli.jrxml",parameters,format);
    }

    private byte[] generateReport(String reportPath,Map<String, Object> parameters, String format) throws Exception {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(reportPath);
        Connection connection = dataSource.getConnection()) {

            if (inputStream == null) {
                throw new FileNotFoundException("No se encontro el classpath: " + reportPath);
            }

            JasperReport report = JasperCompileManager.compileReport(inputStream);
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, connection);

            if (jasperPrint.getPages().isEmpty()){
                System.out.println("El reporte esta vacio");
            } else {
                System.out.println("El reporte contiene " + jasperPrint.getPages().size() + " pagina(s)");
            }

            switch (format.toLowerCase()){
                case "pdf":
                    return JasperExportManager.exportReportToPdf(jasperPrint);
                case "excel":
                    return exportToExcel(jasperPrint);
                default:
                    throw new IllegalArgumentException("Formato no soportado" + format);

            }
        } catch (JRException e){
            throw new JRRuntimeException("Error al generar el reporte "+e.getMessage(), e);
        } catch (SQLException e){
            throw new Exception("Error en la conexion a la base de datos "+e.getMessage(), e);
        }
    }

    private byte[] exportToExcel(JasperPrint jasperPrint) throws JRException {
        try(ByteArrayOutputStream outputStream = new ByteArrayOutputStream()){
            JRXlsxExporter exporter = new JRXlsxExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
            exporter.exportReport();
            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}