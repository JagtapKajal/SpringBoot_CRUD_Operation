package com.company.service;

import com.company.entity.Developer;

import java.util.List;

public interface DeveloperService {

    public String saveDeveloper(Developer developer);

    //method to get all developers

    public List<Developer> getAllDevelopers();

    public Developer getDeveloperById(int id);

    public String deleteById(int id);

    Developer updateDeveloper(int id, Developer newData);

    public void saveAllDeveloper(List<Developer> developers);
}
