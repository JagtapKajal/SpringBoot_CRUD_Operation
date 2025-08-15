package com.company.controller;

import com.company.entity.Developer;
import com.company.repository.DeveloperRepository;
import com.company.service.ExcelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.antlr.v4.runtime.tree.pattern.ParseTreePattern;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.crypt.Encryptor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
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

    public String uploadAndProtectExcel(MultipartFile file, String password) throws Exception {
        // Save Excel data to DB
        excelService.saveExcelData(file); // your existing method

        // Create password-protected copy
        POIFSFileSystem fs = new POIFSFileSystem();
        EncryptionInfo info = new EncryptionInfo(fs);
        Encryptor enc = info.getEncryptor();
        enc.confirmPassword(password);

        try (OPCPackage opc = OPCPackage.open(file.getInputStream());
             OutputStream os = enc.getDataStream(fs)) {
            opc.save(os);
        }

        File protectedFile = new File("protected_" + file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(protectedFile)) {
            fs.writeFilesystem(fos);
        }

        return "Excel data saved to DB and protected file created at: " + protectedFile.getAbsolutePath();
    }

}