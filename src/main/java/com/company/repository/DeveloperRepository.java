package com.company.repository;

import com.company.entity.Developer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeveloperRepository extends JpaRepository<Developer, Integer> {

    //JPQL (Custom Query) uses entity class names and property names, not table or column names
    @Query("SELECT d FROM Developer d WHERE d.age= :age")
    List<Developer> findByAge(@Param("age") int age);

    //Native query means normal SQL query
    @Query(value = "SELECT * FROM Developer",nativeQuery = true)
    List<Developer> findAllDeveloper();

    //Custom query with multiple parameters
    @Query("SELECT d FROM Developer d WHERE d.gender = :gender AND d.city =:city")
    List<Developer> findByGenderAndCity(@Param("gender") String gender, @Param("city") String city);


    //Native SQL Queries with nativeQuery = true
    @Query(value = "SELECT * FROM developer WHERE salary =:salary", nativeQuery = true)
    List<Developer> findBySalary(@Param("salary") Integer salary);
}
