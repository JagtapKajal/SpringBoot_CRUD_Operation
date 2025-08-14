package com.company.controller;

import com.company.service.ExcelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ExcelController {

    @Autowired
    private ExcelService excelService;

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
}