/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zod.SpringBootHibernate.repository;

import com.zod.SpringBootHibernate.model.Employee;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Zod
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {


    
    @Query(value = "SELECT * FROM employees ey ORDER BY id ASC", nativeQuery = true)
    public List<Employee> getAllEmployeesAscending();
    
    @Query(value = "SELECT * FROM employees WHERE email=?1", nativeQuery = true)
    public List<Employee> getEmployeeByEmail(String email);
}
