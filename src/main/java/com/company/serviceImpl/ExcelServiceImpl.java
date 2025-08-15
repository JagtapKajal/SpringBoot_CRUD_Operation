package com.company.serviceImpl;

import com.company.entity.Developer;
import com.company.repository.DeveloperRepository;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.crypt.Encryptor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ExcelServiceImpl {

    @Value("${excel.storage.path}")
    private String excelStoragePath;  // e.g., D:/excel_files/

    @Value("${excel.password}")
    private String excelPassword; // You can set in application.properties

    private DeveloperRepository developerRepository; // replace with your repository

    public ExcelServiceImpl(DeveloperRepository repository) {
        this.developerRepository = repository;
    }

    public String uploadAndSaveToDB(MultipartFile file) throws Exception {
        // Step 1: Save data from Excel to DB
        List<Developer> entities = ExcelHelper.excelToEntities(file.getInputStream());
        developerRepository.saveAll(entities);

        // Step 2: Create plain XLSX file
        String tempFilePath = Paths.get(excelStoragePath, "temp.xlsx").toString();
        createPlainExcelFromDB(tempFilePath);

        // Step 3: Encrypt the XLSX file
        String protectedFilePath = Paths.get(excelStoragePath, "protected.xlsx").toString();
        encryptExcelFile(tempFilePath, protectedFilePath, excelPassword);

        // Step 4: Return file path (or URL)
        return protectedFilePath;
    }

    private void createPlainExcelFromDB(String filePath) throws Exception {
        List<Developer> entities = developerRepository.findAll();

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Data");

        int rowIdx = 0;
        for (Developer e : entities) {
            sheet.createRow(rowIdx++).createCell(0).setCellValue(e.toString()); // Example
        }

        try (FileOutputStream out = new FileOutputStream(filePath)) {
            workbook.write(out);
        }
        workbook.close();
    }

    private void encryptExcelFile(String inputPath, String outputPath, String password) throws Exception {
        POIFSFileSystem fs = new POIFSFileSystem();
        EncryptionInfo info = new EncryptionInfo(fs);
        Encryptor encryptor = info.getEncryptor();
        encryptor.confirmPassword(password);

        try (OPCPackage opc = OPCPackage.open(new File(inputPath), OPCPackage.OpenMode.READ_WRITE);
             OutputStream os = encryptor.getDataStream(fs)) {
            opc.save(os);
        }

        try (FileOutputStream fos = new FileOutputStream(outputPath)) {
            fs.writeFilesystem(fos);
        }
    }
}
