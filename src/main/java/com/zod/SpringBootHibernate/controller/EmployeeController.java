/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zod.SpringBootHibernate.controller;

import com.zod.SpringBootHibernate.exception.ResourceNotFoundException;
import com.zod.SpringBootHibernate.model.Employee;
import com.zod.SpringBootHibernate.repository.EmployeeRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Zod
 */
@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    //get employees
    @GetMapping("employees")
    public List<Employee> getAllEmployee() {
        return this.employeeRepository.findAll();
    }

    //get employees ASCENDING with native query
    @GetMapping("employees/orderAscending")
    public List<Employee> getAllEmployeeAscending() {
        return this.employeeRepository.getAllEmployeesAscending();
    }

    //get employee by id
    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable(value = "id") String employeeId) throws ResourceNotFoundException {
        Employee employee = employeeRepository.findById(UUID.fromString(employeeId)).orElseThrow(() -> new ResourceNotFoundException("Employee not found for id : " + employeeId));
        return ResponseEntity.ok().body(employee);

    }

    //get specific employee with email with native query
    @GetMapping("employees/findByEmail/{email}")
    public ResponseEntity<List<Employee>> getEmployeeByEmail(@PathVariable(value = "email") String email) throws ResourceNotFoundException {
        List<Employee> found_employees = employeeRepository.getEmployeeByEmail(email);

        if (found_employees.size() > 0) {

            return ResponseEntity.ok().body(found_employees);
        } else {
            throw new ResourceNotFoundException("Employee not found for email : " + email);
        }
    }

    // save employee
    @PostMapping("employees")
    public Employee createEmployee(@RequestBody Employee employee) {
        return this.employeeRepository.save(employee);
    }

    // update employee
    @PutMapping("employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") String employeeId, @RequestBody Employee employeeDetails) throws ResourceNotFoundException {
        Employee employee = employeeRepository.findById(UUID.fromString(employeeId)).orElseThrow(() -> new ResourceNotFoundException("Employee not found for id : " + employeeId));

        employee.setEmail(employeeDetails.getEmail());
        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());

        return ResponseEntity.ok(this.employeeRepository.save(employee));
    }

    //delete employee
    @DeleteMapping("employees/{id}")
    public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") String employeeId) throws ResourceNotFoundException {
        Employee employee = employeeRepository.findById(UUID.fromString(employeeId)).orElseThrow(() -> new ResourceNotFoundException("Employee not found for id : " + employeeId));

        this.employeeRepository.delete(employee);

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);

        return response;
    }
}
