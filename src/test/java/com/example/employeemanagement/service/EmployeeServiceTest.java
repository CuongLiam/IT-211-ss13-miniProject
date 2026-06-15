package com.example.employeemanagement.service;

import com.example.employeemanagement.model.Employee;
import com.example.employeemanagement.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    void getAllEmployees_ReturnList() {
        List<Employee> employees = List.of(
                new Employee(1L, "Nguyen Van A", "Engineering", 15000000.0),
                new Employee(2L, "Tran Thi B", "Human Resources", 12000000.0)
        );
        when(employeeRepository.findAll()).thenReturn(employees);

        List<Employee> result = employeeService.getAllEmployees();

        assertFalse(result.isEmpty());
        assertEquals(2, result.size());
        verify(employeeRepository).findAll();
    }

    @Test
    void getById_Found() {
        Employee employee = new Employee(1L, "Nguyen Van A", "Engineering", 15000000.0);
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        Employee result = employeeService.getById(1L);

        assertEquals(1L, result.getId());
        assertEquals("Nguyen Van A", result.getFullName());
        verify(employeeRepository).findById(1L);
    }

    @Test
    void getById_NotFound_ThrowException() {
        when(employeeRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> employeeService.getById(99L));
        verify(employeeRepository).findById(99L);
    }

    @Test
    void addEmployee_Success() {
        Employee request = new Employee(null, "Le Van C", "Finance", 13500000.0);
        Employee savedEmployee = new Employee(3L, "Le Van C", "Finance", 13500000.0);
        when(employeeRepository.save(request)).thenReturn(savedEmployee);

        Employee result = employeeService.addEmployee(request);

        assertEquals(3L, result.getId());
        assertEquals("Le Van C", result.getFullName());
        verify(employeeRepository).save(request);
    }

    @Test
    void deleteEmployee_RemovesCorrectElement() {
        Employee employee = new Employee(1L, "Nguyen Van A", "Engineering", 15000000.0);
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        employeeService.deleteEmployee(1L);

        verify(employeeRepository).findById(1L);
        verify(employeeRepository).delete(employee);
    }
}
