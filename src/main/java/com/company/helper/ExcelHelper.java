package com.company.helper;

import com.company.entity.Developer;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelHelper {

    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    public static boolean hasExcelFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }

    public static List<Developer> convertExcelTOListDeveloper(InputStream is) {
        try {
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();

            List<Developer> developers = new ArrayList<>();
            int rowNumber = 0;

            while (rows.hasNext()) {
                Row currentRow = rows.next();

                // Skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Developer developer = new Developer();

                developer.setId((int) currentRow.getCell(0).getNumericCellValue());
                developer.setAge((int) currentRow.getCell(1).getNumericCellValue());
                developer.setCity(currentRow.getCell(2).getStringCellValue());
                developer.setFName(currentRow.getCell(3).getStringCellValue());
                developer.setGender(currentRow.getCell(4).getStringCellValue());
                developer.setLName(currentRow.getCell(5).getStringCellValue());
                developer.setSalary((long) currentRow.getCell(6).getNumericCellValue());

                developers.add(developer);
            }

            workbook.close();
            return developers;

        } catch (IOException e) {
            throw new RuntimeException("Failed to parse Excel file: " + e.getMessage());
        }
    }
}

