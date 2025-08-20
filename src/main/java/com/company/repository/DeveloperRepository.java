package com.company.repository;

import com.company.entity.Developer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeveloperRepository extends JpaRepository<Developer, Integer> {

    @Query("SELECT d FROM Developer d WHERE d.age= :age")
    List<Developer> findByAge(@Param("age") int age);

    @Query(value = "SELECT * FROM Developer",nativeQuery = true)
    List<Developer> findAllDeveloper();
}
