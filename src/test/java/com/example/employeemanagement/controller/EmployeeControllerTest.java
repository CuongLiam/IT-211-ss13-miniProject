package com.example.employeemanagement.controller;

import com.example.employeemanagement.exception.EmployeeNotFoundException;
import com.example.employeemanagement.exception.GlobalExceptionHandler;
import com.example.employeemanagement.model.Employee;
import com.example.employeemanagement.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
@Import(GlobalExceptionHandler.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EmployeeService employeeService;

    @Test
    void getAllEmployees_ReturnHttp200AndJsonList() throws Exception {
        List<Employee> employees = List.of(
                new Employee(1L, "Nguyen Van A", "Engineering", 15000000.0),
                new Employee(2L, "Tran Thi B", "Human Resources", 12000000.0)
        );
        when(employeeService.getAllEmployees()).thenReturn(employees);

        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].fullName").value("Nguyen Van A"))
                .andExpect(jsonPath("$[1].department").value("Human Resources"));
    }

    @Test
    void getEmployeeById_Found_ReturnHttp200() throws Exception {
        Employee employee = new Employee(1L, "Nguyen Van A", "Engineering", 15000000.0);
        when(employeeService.getById(1L)).thenReturn(employee);

        mockMvc.perform(get("/api/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.fullName").value("Nguyen Van A"));
    }

    @Test
    void getEmployeeById_NotFound_ReturnHttp404() throws Exception {
        when(employeeService.getById(99L)).thenThrow(new EmployeeNotFoundException("Employee not found with id: 99"));

        mockMvc.perform(get("/api/employees/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Employee not found with id: 99"));
    }

    @Test
    void addEmployee_Success_ReturnHttp201() throws Exception {
        Employee request = new Employee(null, "Le Van C", "Finance", 13500000.0);
        Employee response = new Employee(3L, "Le Van C", "Finance", 13500000.0);
        when(employeeService.addEmployee(any(Employee.class))).thenReturn(response);

        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.department").value("Finance"));
    }
}
