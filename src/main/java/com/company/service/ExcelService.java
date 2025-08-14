package com.company.service;

import com.company.entity.Developer;
import com.company.repository.DeveloperRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ExcelService {

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
    }
}