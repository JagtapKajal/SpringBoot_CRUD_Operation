package com.company.service;

import com.company.entity.Developer;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DeveloperService {

    // It will save one Developer details at a time
    public String saveDeveloper(Developer developer);

    //method to get all developers
    public List<Developer> getAllDevelopers();

    // method to get developer by id
    public Developer getDeveloperById(int id);

    // Delete Developers By id
    public String deleteById(int id);

    //Update Developer Details using Setter/Getter
    Developer updateDeveloper(int id, Developer newData);

    // It will save All Developers(List of Developers)
    public void saveAllDeveloper(List<Developer> developers);

    // to handle not exits id in DB
    public String DeleteById(int id);

    //filter/get only city from database
    List<Developer> filterByCity(String city);

    //filter/get only gender from database
    List<Developer> filterByGender(String gender);

    // filter Developer by their FirstName
    List<Developer> filterByFirstName(String fName);

    // filter developer by their LastName
    List<Developer> filterByLastName(String lName);

    List<Developer> getDeveloperByAge(int age);
}

