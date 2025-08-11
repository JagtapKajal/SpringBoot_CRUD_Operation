package com.company.controller;

import com.company.entity.Developer;
import com.company.service.DeveloperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/developer")
public class DeveloperController {

    @Autowired
    private DeveloperService developerService;

    //  Create/add Developer
    @PostMapping("/addData")
    public ResponseEntity<String> addDeveloper(@RequestBody Developer developer) {

        System.err.println(developer);

        String data = developerService.saveDeveloper(developer);
        return new ResponseEntity<>(data, HttpStatus.CREATED);

    }

    // Get All Developers
    @GetMapping("/getAllDeveloper")
    public ResponseEntity<List<Developer>> getAllDevelopers() {

        List<Developer> dlist = developerService.getAllDevelopers();
        return new ResponseEntity<>(dlist, HttpStatus.OK);
    }

    //get developer by id
    @GetMapping("/getById/{id}")
    public ResponseEntity<Developer> getDeveloperById(@PathVariable int id) {
        Developer developer = developerService.getDeveloperById(id);
        if (developer != null) {
            return ResponseEntity.ok(developer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // delete developer by id
    @DeleteMapping("/DeleteById/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") int id) {
        String msg = developerService.deleteById(id);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }


    // if user enter id which is not exist in DB
    @GetMapping("/DeleteById/{id}")
    public ResponseEntity<String> DeleteById(@PathVariable("id") int id) {
        String str = developerService.DeleteById(id);
        return new ResponseEntity<>(str, HttpStatus.OK);
    }

    // Update Developers by id
    @PutMapping("/updateDeveloper/{id}")
    public ResponseEntity<Developer> updateById(@PathVariable int id, @RequestBody Developer developer) {
        Developer dev = developerService.updateDeveloper(id, developer);
        return new ResponseEntity<>(dev, HttpStatus.OK);
    }

    // save All Developers List
    @GetMapping("/saveAllDeveloper")
    public ResponseEntity<String> getAllDevelopers(List<Developer> developers) {
        developerService.saveAllDeveloper(developers);
        return new ResponseEntity<>("Developer data saved", HttpStatus.CREATED);
    }


    // to get city and gender in single API
    @GetMapping("/filter")
    public ResponseEntity<List<Developer>> filteredList(@RequestParam(required = false) String city,
                                                        @RequestParam(required = false) String gender) {

        // findAll() developers
        List<Developer> allDevelopers = developerService.getAllDevelopers();

        List<Developer> filteredList = allDevelopers;

        // List<Developer> filteredList = developerService.filterByCity(city);

        List<Developer> sortedList = new ArrayList<>();
        if (city != null && gender != null) {

            filteredList = allDevelopers.stream()
                    .filter(dev -> dev.getCity().equalsIgnoreCase(city) && dev.getGender().equalsIgnoreCase(gender))
                    .collect(Collectors.toList());
        } else if (city != null) {
            sortedList = developerService.filterByCity(city);
        } else if (gender != null) {
            sortedList = developerService.filterByGender(gender);
        } else {
            return new ResponseEntity<>(allDevelopers, HttpStatus.BAD_REQUEST);
        }

        if (filteredList.isEmpty()) {

            return new ResponseEntity<>(filteredList, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(filteredList, HttpStatus.OK);

    }

}
