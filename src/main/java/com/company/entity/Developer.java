package com.company.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Entity
// Lombok dependency will automatically add the Getter and Setters
@Getter
@Setter
@ToString
public class Developer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@JsonProperty(access = JsonProperty.Access.READ_ONLY)
    //This property for read only id from swagger
    private int id;

    private String fName;

    private String lName;

    private int age;

    private String city;

    private String gender;

    private long salary;

    private String developerId;

    private int yearOfBirth;

    private String dob;

    private String DateAndTime;


}
