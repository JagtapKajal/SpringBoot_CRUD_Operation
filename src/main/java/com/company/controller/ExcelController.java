package com.company.controller;

import com.company.helper.ExcelHelper;
import com.company.service.ExcelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/excel")
public class ExcelController {

    private final ExcelService excelService;

    public ExcelController(ExcelService excelService) {
        this.excelService = excelService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadExcelFile(@RequestParam("file") MultipartFile file) {
        if (ExcelHelper.hasExcelFormat(file)) {
            excelService.save(file);
            return ResponseEntity.ok("Excel file uploaded and data saved to database.");
        }
        return ResponseEntity.badRequest().body("Please upload an Excel file!");
    }
}
