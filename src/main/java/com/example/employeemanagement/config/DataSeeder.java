package com.example.employeemanagement.config;

import com.example.employeemanagement.model.Employee;
import com.example.employeemanagement.repository.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedEmployees(EmployeeRepository employeeRepository) {
        return args -> {
            if (employeeRepository.count() == 0) {
                employeeRepository.saveAll(List.of(
                        new Employee(null, "Nguyen Van A", "Engineering", 15000000.0),
                        new Employee(null, "Tran Thi B", "Human Resources", 12000000.0),
                        new Employee(null, "Le Van C", "Finance", 13500000.0),
                        new Employee(null, "Pham Thi D", "Marketing", 11000000.0),
                        new Employee(null, "Hoang Van E", "Engineering", 18000000.0)
                ));
            }
        };
    }
}
