package com.company.controller;

import com.company.helper.ExcelHelper;
import com.company.service.DeveloperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@CrossOrigin("*")
public class ExcelController {

    @Autowired
    private DeveloperService developerService;

    @PostMapping("Developer/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        if (ExcelHelper.checkExcelFormat(file)) {

            this.developerService.saveDetail(file);

            return ResponseEntity.ok(Map.of("Message", "File is Uploaded and data is saved to Database"));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please Upload Excel File");
    }



}