package com.example.employeemanagement.service;

import com.example.employeemanagement.exception.EmployeeNotFoundException;
import com.example.employeemanagement.model.Employee;
import com.example.employeemanagement.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private static final Logger log = LoggerFactory.getLogger(EmployeeService.class);

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getById(Long id) {
        return employeeRepository.findById(id).orElseThrow(() -> {
            log.warn("Employee not found with id: {}", id);
            return new EmployeeNotFoundException("Employee not found with id: " + id);
        });
    }

    public Employee addEmployee(Employee employee) {
        try {
            employee.setId(null);
            return employeeRepository.save(employee);
        } catch (Exception exception) {
            log.error("Error while adding employee: {}", exception.getMessage(), exception);
            throw exception;
        }
    }

    public Employee updateEmployee(Long id, Employee request) {
        try {
            Employee employee = getById(id);
            employee.setFullName(request.getFullName());
            employee.setDepartment(request.getDepartment());
            employee.setSalary(request.getSalary());
            return employeeRepository.save(employee);
        } catch (EmployeeNotFoundException exception) {
            throw exception;
        } catch (Exception exception) {
            log.error("Error while updating employee with id {}: {}", id, exception.getMessage(), exception);
            throw exception;
        }
    }

    public void deleteEmployee(Long id) {
        try {
            Employee employee = getById(id);
            employeeRepository.delete(employee);
        } catch (EmployeeNotFoundException exception) {
            throw exception;
        } catch (Exception exception) {
            log.error("Error while deleting employee with id {}: {}", id, exception.getMessage(), exception);
            throw exception;
        }
    }
}
