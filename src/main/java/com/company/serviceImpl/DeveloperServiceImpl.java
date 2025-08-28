package com.company.serviceImpl;

import com.company.entity.Developer;
import com.company.repository.DeveloperRepository;
import com.company.service.DeveloperService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Collections;
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
    public List<Developer> AddAllDevelopers(List<Developer> developer) {
        List<Developer> developerlist = developerRepository.saveAll(developer);
        return developerlist;
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

//    @Override
//    public List<Developer> getDeveloperByAge(int age) {
//        List<Developer> developerByAge = developerRepository.findByAge(age);
//        return developerByAge;
//
//    }

    @Override
    public List<Developer> getAllDeveloper() {
        return developerRepository.findAllDeveloper();
    }

    @Override
    public List<Developer> getDeveloperByGenderAndCity(String gender, String city) {
        return developerRepository.findByGenderAndCity(gender, city);
    }

    @Override
    public List<Developer> findBySalary(Integer salary) {
        List<Developer> list =(developerRepository.findBySalary(salary));
        return list ;
    }

    @Override
    @Transactional
    public void updateCity(int id, String city) {
        developerRepository.updateDeveloperCityById(id, city);
    }

    @Override
    public List<Developer> findByDeveloperIdIsNullOrDeveloperIdEquals(String empty) {
        return developerRepository.findByDeveloperIdIsNullOrDeveloperIdEquals("");
    }

    @Scheduled(fixedRate = 10000)
    public void schedular(){
        System.out.println("Hello  ");

    }

    // Runs every day at midnight (00:00) and display msg if developer id is missing
    @Scheduled(cron = "0 0 0 * * ?")
    public void updateAges() {
        List<Developer> developers = developerRepository.findAll();
        LocalDate today = LocalDate.now();

        for (Developer dev : developers) {
            if (dev.getDob() != null &&
                    dev.getDob().getDayOfMonth() == today.getDayOfMonth() &&
                    dev.getDob().getMonth() == today.getMonth()) {

                dev.setAge(dev.getAge() + 1); // increase age
                developerRepository.save(dev); // update in DB
            }
        }
        if(!developers.isEmpty()){
            System.out.println("Found developers with missing ID: "+developers.size());
            developers.forEach(dev->{System.out.println("Missing developer Id-> name: "+dev.getFName() + " " + dev.getLName());});
        }
        else{
            System.out.println("All developers have valid Id ");
        }
    }
}
