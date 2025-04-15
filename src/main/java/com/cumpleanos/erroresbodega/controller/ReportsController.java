/*
 * Copyright (c) 2024 Luis Chumi.
 * Este software está licenciado bajo la Licencia Pública General de GNU versión 3. Puedes encontrar una copia de la licencia en https://www.gnu.org/licenses/gpl-3.0.html.
 *
 * Para consultas o comentarios, puedes contactarme en "luischumi.9@gmail.com".
 * Me gustaría ser reconocido por mi trabajo y estar abierto a colaboraciones o enseñanzas sobre el programa.
 */

package com.cumpleanos.erroresbodega.controller;

import com.cumpleanos.erroresbodega.models.Items;
import com.cumpleanos.erroresbodega.services.ResportsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("reports")
@CrossOrigin("*")
@RequiredArgsConstructor
public class ReportsController {

    private final ResportsService reportsService;

    @GetMapping("/report/pdf")
    public void getReport(@RequestParam String codigo, HttpServletResponse response) throws Exception {
        try {
            byte[] pdfBytes = reportsService.getReport(codigo,"pdf");

            response.setContentType("application/pdf");
            response.setContentLength(pdfBytes.length);
            response.getOutputStream().write(pdfBytes);
            response.getOutputStream().flush();
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al generar el reporte: " + e.getMessage());
        }
    }

    @PostMapping("/products-images/excel")
    public void getReportImages(@RequestBody Items items, HttpServletResponse response) throws Exception {
        try {
            byte[] excelBytes = reportsService.getReportByImages(items,"excel");

            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=report.xlsx");
            response.setContentLength(excelBytes.length);
            response.getOutputStream().write(excelBytes);
            response.getOutputStream().flush();
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al generar el reporte en Excel: " + e.getMessage());
        }
    }

    @GetMapping("/report/excel")
    public void getExcelReport(@RequestParam String codigo, HttpServletResponse response) throws Exception {
        try {
            byte[] excelBytes = reportsService.getReport(codigo, "excel");

            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=report.xlsx");
            response.setContentLength(excelBytes.length);
            response.getOutputStream().write(excelBytes);
            response.getOutputStream().flush();
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al generar el reporte en Excel: " + e.getMessage());
        }
    }

    @GetMapping("/report/pedido/pdf")
    public void getReportCli(@RequestParam String pedido, HttpServletResponse response) throws Exception {
        try {
            byte[] pdfBytes = reportsService.getReportClientePedido(pedido,"pdf");

            response.setContentType("application/pdf");
            response.setContentLength(pdfBytes.length);
            response.getOutputStream().write(pdfBytes);
            response.getOutputStream().flush();
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al generar el reporte: " + e.getMessage());
        }
    }

    @GetMapping("/report/pedido/excel")
    public void getExcelReportCli(@RequestParam String pedido, HttpServletResponse response) throws Exception {
        try {
            byte[] excelBytes = reportsService.getReportClientePedido(pedido, "excel");

            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=report.xlsx");
            response.setContentLength(excelBytes.length);
            response.getOutputStream().write(excelBytes);
            response.getOutputStream().flush();
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al generar el reporte en Excel: " + e.getMessage());
        }
    }


}
