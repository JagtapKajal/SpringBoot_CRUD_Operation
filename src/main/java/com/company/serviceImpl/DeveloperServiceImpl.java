package com.company.serviceImpl;

import com.company.entity.Developer;
import com.company.repository.DeveloperRepository;
import com.company.service.DeveloperService;
import org.springframework.beans.factory.annotation.Autowired;
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

        Developer saveDeveloper = developerRepository.save(developer);
        return "Developer saved";
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

}
