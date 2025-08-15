package com.company.serviceImpl;

import com.company.service.ExcelService;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.crypt.Encryptor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

@Service
public class ExcelServiceImpl extends ExcelService {

    @Override
    public String uploadAndProtectExcel(MultipartFile file, String password) throws Exception {
        // Create POI filesystem for encryption
        POIFSFileSystem fs = new POIFSFileSystem();
        EncryptionInfo info = new EncryptionInfo(fs);
        Encryptor enc = info.getEncryptor();
        enc.confirmPassword(password);

        // Write the uploaded file content into encrypted stream
        try (OPCPackage opc = OPCPackage.open(file.getInputStream());
             OutputStream os = enc.getDataStream(fs)) {
            opc.save(os);
        }

        // Save to disk (you can save to DB, S3, etc.)
        File protectedFile = new File("protected_" + file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(protectedFile)) {
            fs.writeFilesystem(fos);
        }

        return "File saved at: " + protectedFile.getAbsolutePath();
    }
}
