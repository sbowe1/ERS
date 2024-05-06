package com.revature.EmployeeReimbursementSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.revature.models")
@ComponentScan("com.revature")
@EnableJpaRepositories("com.revature.daos")
public class EmployeeReimbursementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeReimbursementSystemApplication.class, args);
	}

}
