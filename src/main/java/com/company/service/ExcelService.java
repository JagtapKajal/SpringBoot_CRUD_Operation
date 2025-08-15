package com.company.service;

import com.company.entity.Developer;
import com.company.repository.DeveloperRepository;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.crypt.Encryptor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@Service
public abstract class ExcelService {

    @Autowired
    private DeveloperRepository developerRepository;

    public void saveExcelData(MultipartFile file) throws IOException {
        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);

            // read the Excel file data
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);

                if (row != null) {
                    Developer developer = new Developer();

                    developer.setFName(row.getCell(0).getStringCellValue());
                    developer.setLName(row.getCell(1).getStringCellValue());
                    developer.setAge((int) row.getCell(2).getNumericCellValue());
                    developer.setCity(row.getCell(3).getStringCellValue());
                    developer.setGender(row.getCell(4).getStringCellValue());
                    developer.setSalary((long) row.getCell(5).getNumericCellValue());

                    developerRepository.save(developer);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to Add excel file ");
        }
        // After saving to DB, create password-protected copy
        try {
            POIFSFileSystem fs = new POIFSFileSystem();
            EncryptionInfo info = new EncryptionInfo(fs);
            Encryptor enc = info.getEncryptor(); // ✅ correct class name
            String password = "Excel@123";
            enc.confirmPassword(password);

            try (OPCPackage opc = OPCPackage.open(file.getInputStream());
                 OutputStream os = enc.getDataStream(fs)) {
                opc.save(os);
            }

            File protectedFile = new File("protected_" + file.getOriginalFilename());
            try (FileOutputStream fos = new FileOutputStream(protectedFile)) {
                fs.writeFilesystem(fos);
            }

        } catch (Exception e) {
            throw new RuntimeException("Data saved to DB, but failed to create password-protected file: " + e.getMessage());
        }

    }

    public abstract String uploadAndProtectExcel(MultipartFile file, String password) throws Exception;
}