package com.rves.controller;

import com.rves.services.ReportBuilder;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private ReportBuilder reportBuilder;


    @RequestMapping("/example")
    public void example(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=example.xlsx");

        XSSFWorkbook workbook = reportBuilder.generateExample();

        ServletOutputStream out = response.getOutputStream();
        workbook.write(out);

    }

}
