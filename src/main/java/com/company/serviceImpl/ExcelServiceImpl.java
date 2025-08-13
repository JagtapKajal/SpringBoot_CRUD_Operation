package com.company.serviceImpl;

import com.company.entity.Developer;
import com.company.helper.ExcelHelper;
import com.company.repository.DeveloperRepository;
import com.company.service.ExcelService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ExcelServiceImpl implements ExcelService {

    private final DeveloperRepository developerRepository;

    public ExcelServiceImpl(DeveloperRepository developerRepository) {
        this.developerRepository = developerRepository;
    }

    @Override
    public void save(MultipartFile file) {
        try {
            List<Developer> developers = ExcelHelper.convertExcelTOListDeveloper(file.getInputStream());
            developerRepository.saveAll(developers);
        } catch (IOException e) {
            throw new RuntimeException("Failed to store Excel data: " + e.getMessage());
        }
    }
}

