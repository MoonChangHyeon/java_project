package com.example.fortifyapp.controller;

import com.example.fortifyapp.entity.Vulnerability;
import com.example.fortifyapp.repository.VulnerabilityRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.util.List;

@RestController
@RequestMapping("/api/report")
public class ReportController {

    private final VulnerabilityRepository repository;

    public ReportController(VulnerabilityRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<byte[]> downloadXlsx() throws Exception {
        List<Vulnerability> list = repository.findAll();
        try (XSSFWorkbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            XSSFSheet sheet = workbook.createSheet("Vulnerabilities");
            int rowIdx = 0;
            Row header = sheet.createRow(rowIdx++);
            header.createCell(0).setCellValue("CWE");
            header.createCell(1).setCellValue("Severity");
            header.createCell(2).setCellValue("Path");

            for (Vulnerability v : list) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(v.getCweId());
                row.createCell(1).setCellValue(v.getSeverity());
                row.createCell(2).setCellValue(v.getFilePath());
            }
            workbook.write(out);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=vulnerabilities.xlsx");
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                    .body(out.toByteArray());
        }
    }
}
