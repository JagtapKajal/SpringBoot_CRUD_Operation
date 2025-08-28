package com.company;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringCrudOperationApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCrudOperationApplication.class, args);
		System.out.println("This is my Project");
		System.out.println("Project");
		System.out.println("Spring Project");
	}

}
