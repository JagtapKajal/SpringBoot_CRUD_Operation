package com.company.helper;

import com.company.controller.DeveloperController;
import com.company.entity.Developer;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelHelper {

    //check that file is of excel type or not
    public static boolean checkExcelFormat(MultipartFile file) {
        String contentType = file.getContentType();

        return contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    public List<Developer> convertExcelTOListDeveloper(InputStream is) {

        List<Developer> developerList = new ArrayList<>();

        try {

            XSSFWorkbook workbook = new XSSFWorkbook(is);

            XSSFSheet database = workbook.getSheet("Developer Database");

            int rowNumber = 0;

            Iterator<Row> iterator = database.iterator();

            //it will return true or false and move to next row
            while(iterator.hasNext()) {

                Row row = iterator.next();

                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cells = row.iterator();

                int count = 0;

                Developer dev = new Developer();
                while (cells.hasNext()) {
                    Cell cell = cells.next();

                    switch (count) {

                        case 0:
                            dev.setId((int) cell.getNumericCellValue());
                            break;
                        case 1:
                            dev.setAge((int) cell.getNumericCellValue());
                            break;
                        case 2:
                            dev.setCity(cell.getStringCellValue());
                            break;
                        case 3:
                            dev.setFName(cell.getStringCellValue());
                            break;
                        case 4:
                            dev.setGender(cell.getStringCellValue());
                            break;
                        case 5:
                            dev.setLName(cell.getStringCellValue());
                            break;
                        case 6:
                            dev.setSalary((int) cell.getNumericCellValue());
                            break;
                        default:
                            System.out.println("Enter Valid Choice");
                    }

                    count++;
                }
                developerList.add(dev);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return developerList;
    }
}
