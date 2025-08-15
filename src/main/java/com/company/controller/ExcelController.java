package com.company.controller;

import com.company.entity.Developer;
import com.company.repository.DeveloperRepository;
import com.company.service.ExcelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.antlr.v4.runtime.tree.pattern.ParseTreePattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class ExcelController {

    @Autowired
    private ExcelService excelService;

    @Autowired
    private DeveloperRepository developerRepository;

    @Operation(summary = "Upload Excel File")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Uploaded successfully")
    })

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String upload(@RequestParam("file") MultipartFile file)
    {
        try{
            excelService.saveExcelData(file);
            return "Excel data Uploaded : ";
        }
        catch (Exception e) {
            return "Error : Can not upload data : " + e.getMessage();
        }
    }
    @GetMapping("/all")
    public ResponseEntity<List<Developer>> getAllFromExcel() {

        return ResponseEntity.ok(developerRepository.findAll());
    }

}