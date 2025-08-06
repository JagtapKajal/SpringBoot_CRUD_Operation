package com.company.serviceImpl;

import com.company.entity.Developer;
import com.company.helper.DeveloperIdGenerator;
import com.company.repository.DeveloperRepository;
import com.company.service.DeveloperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeveloperServiceImpl implements DeveloperService {

    //Injects the DeveloperRepository bean
    @Autowired
    private DeveloperRepository developerRepository;

    // Save a new Developers
    @Override
    public String saveDeveloper(Developer developer) {

        String devId = DeveloperIdGenerator.generateId(developer);
        developer.setDeveloperId(devId);
        Developer developer1 = developerRepository.save(developer);
        return "Hey " + developer1.getfName() + " Your id is: " + developer1.getDeveloperId();
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

        developer.setfName(newData.getfName());
        developer.setlName(newData.getlName());
        developer.setCity(newData.getCity());
        developer.setAge(newData.getAge());
        developer.setSalary(newData.getSalary());

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


}
