package com.company.controller;

import com.company.entity.Developer;
import com.company.service.DeveloperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/developer")
public class DeveloperController {

    @Autowired
    private DeveloperService developerService;

    //  Create Developer
    @PostMapping("/addData")
    public ResponseEntity<String> addDeveloper(@RequestBody Developer developer) {

        System.err.println(developer);
        developerService.saveDeveloper(developer);
        return new ResponseEntity<>("Developer data saved", HttpStatus.CREATED);

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

    @PutMapping("/updateDevloper/{id}")
    public ResponseEntity<Developer> updateById(@PathVariable int id, @RequestBody Developer developer) {
        Developer dev = developerService.updateDeveloper(id, developer);
        return new ResponseEntity<>(dev, HttpStatus.OK);
    }

    public ResponseEntity<String> getAllDevelopers(List<Developer> developers) {
        developerService.saveAllDeveloper(developers);
        return new ResponseEntity<>("Developer data saved", HttpStatus.CREATED);
    }
}
