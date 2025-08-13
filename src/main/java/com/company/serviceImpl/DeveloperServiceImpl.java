package com.company.serviceImpl;

import com.company.entity.Developer;
import com.company.helper.DeveloperIdGenerator;
import com.company.helper.ExcelHelper;
import com.company.repository.DeveloperRepository;
import com.company.service.DeveloperService;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DeveloperServiceImpl implements DeveloperService {

    //Injects the DeveloperRepository bean
    @Autowired
    private DeveloperRepository developerRepository;

    //    // Save a new Developers
    @Override
    public String saveDeveloper(Developer developer) {
//
//        String devId = DeveloperIdGenerator.generateId(developer);
//        developer.setDeveloperId(devId);
//        Developer developer1 = developerRepository.save(developer);
        return "Hey ";
    }

    // Fet All Developers
    @Override
    public List<Developer> getAllDevelopers() {

        List<Developer> developerList = developerRepository.findAll();
        return developerList;
    }

    @Override
    public Developer getDeveloperById(int id) {
        Optional<Developer> optional = developerRepository.findById(id);
        return optional.orElse(null);
    }

    //Delete Single Developer which is exit in DB
    @Override
    public String deleteById(int id) {
        developerRepository.deleteById(id);
        return "Developer deleted";
    }

    //Update Developers By id
    @Override
    public Developer updateDeveloper(int id, Developer newData) {

        Developer developer = developerRepository.findById(id).orElseThrow(()
                -> new NullPointerException("id is not found" + id));

        developer.setFName(newData.getFName());
        developer.setLName(newData.getLName());
        developer.setCity(newData.getCity());
        developer.setAge(newData.getAge());
        Developer updateDeveloper = developerRepository.save(developer);
        return updateDeveloper;
    }

    // Save List of Developers
    @Override
    public void saveAllDeveloper(List<Developer> developers) {
        developerRepository.saveAll(developers);
    }

    // if user enter id which is not exist in DB for this problem handle exception
    @Override
    public String DeleteById(int id) {
        Developer developer = developerRepository.findById(id).orElseThrow(()
                -> new NullPointerException("id Does Not Exits in DB" + id));
        developerRepository.deleteById(id);
        return "Developer Deleted Successfully";
    }

    //It will filter only city from DB
    @Override
    public List<Developer> filterByCity(String city) {

        List<Developer> DeveloperList = developerRepository.findAll();
        List<Developer> filterList = DeveloperList.stream().filter(developer ->
                developer.getCity().equalsIgnoreCase(city)).collect(Collectors.toList());
        return filterList;
    }

    //It will filter only gender from DB
    @Override
    public List<Developer> filterByGender(String gender) {

        List<Developer> DeveloperList = developerRepository.findAll();
        List<Developer> filterList = DeveloperList.stream().filter(developer ->
                developer.getGender().equalsIgnoreCase(gender)).collect(Collectors.toList());
        return filterList;
    }

    @Override
    public List<Developer> filterByFirstName(String fName) {
        List<Developer> filteredStudents = developerRepository.findAll().stream()
                .filter((k -> fName.equalsIgnoreCase(k.getFName())))
                .collect(Collectors.toList());
        return filteredStudents;

    }

    @Override
    public List<Developer> filterByLastName(String lName) {
        List<Developer> filteredDeveloper = developerRepository.findAll().stream()
                .filter((k -> lName.equalsIgnoreCase(k.getLName())))
                .collect(Collectors.toList());
        return filteredDeveloper;
    }


    // to get excel data
    @Override
    public void saveDetail(MultipartFile file) {

        try {
            List<Developer> developers = ExcelHelper.convertExcelTOListDeveloper(file.getInputStream());
            this.developerRepository.saveAll(developers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Developer> getAllDev() {
        return this.developerRepository.findAll();
    }


}
