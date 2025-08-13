package com.company.controller;

import com.company.helper.ExcelHelper;
import com.company.service.ExcelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload Excel file", description = "Uploads an Excel file and stores data in the database")
    public ResponseEntity<String> uploadExcelFile( @Parameter(description = "Excel file to upload", required = true)
                                                       @RequestParam("file") MultipartFile file) {
        if (ExcelHelper.hasExcelFormat(file)) {
            try {
                excelService.save(file);
                return ResponseEntity.ok("Excel file uploaded and data saved to database.");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                        .body("Could not upload the file: " + e.getMessage());
            }
        }
        return ResponseEntity.badRequest().body("Please upload an Excel file!");
    }
}
