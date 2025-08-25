package com.company.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;


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

    @Transient
    private int age;

    private String city;

    private String gender;

    private long salary;

    private String developerId;

    private int yearOfBirth;

    private LocalDate dob;

    private LocalDateTime DateAndTime;


}
