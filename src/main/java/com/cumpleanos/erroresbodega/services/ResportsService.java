/*
 * Copyright (c) 2024 Luis Chumi.
 * Este software está licenciado bajo la Licencia Pública General de GNU versión 3. Puedes encontrar una copia de la licencia en https://www.gnu.org/licenses/gpl-3.0.html.
 *
 * Para consultas o comentarios, puedes contactarme en "luischumi.9@gmail.com".
 * Me gustaría ser reconocido por mi trabajo y estar abierto a colaboraciones o enseñanzas sobre el programa.
 */

package com.cumpleanos.erroresbodega.services;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.ExporterInput;
import net.sf.jasperreports.export.OutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Service
public class ResportsService {

    @Autowired
    private DataSource dataSource;

    public byte[] getReport(String empresa, String cco, String format) throws Exception {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("P_EMPRESA", empresa);
        parameters.put("P_CODIGO", cco);

        System.out.println("Parámetros enviados: P_EMPRESA=" + empresa + ", P_CODIGO=" + cco);

        return generateReport(parameters, format);
    }

    private byte[] generateReport(Map<String, Object> parameters, String format) throws Exception {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("reports/pedidos.jrxml");
        if (inputStream == null) {
            throw new FileNotFoundException("El archivo 'pedidos.jrxml' no se encontró en el classpath.");
        }

        JasperReport report = JasperCompileManager.compileReport(inputStream);

        try (Connection connection = dataSource.getConnection()) {
            System.out.println("Conexión a la base de datos exitosa");

            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, connection);
            if (jasperPrint.getPages().isEmpty()) {
                System.out.println("El reporte está vacío");
            } else {
                System.out.println("El reporte contiene " + jasperPrint.getPages().size() + " página(s)");
            }

            if ("pdf".equalsIgnoreCase(format)) {
                return JasperExportManager.exportReportToPdf(jasperPrint);
            } else if ("excel".equalsIgnoreCase(format)) {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                JRXlsxExporter exporter = new JRXlsxExporter();
                ExporterInput exporterInput = new SimpleExporterInput(jasperPrint);
                OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(outputStream);
                exporter.setExporterInput(exporterInput);
                exporter.setExporterOutput(exporterOutput);
                exporter.exportReport();
                return outputStream.toByteArray();
            } else {
                throw new IllegalArgumentException("Formato no soportado: " + format);
            }
        } catch (JRException e) {
            throw new Exception("Error en JasperReports: " + e.getMessage(), e);
        } catch (SQLException e) {
            throw new Exception("Error en la conexión a la base de datos: " + e.getMessage(), e);
        }
    }


}