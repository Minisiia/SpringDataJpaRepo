package com.example.springdatajpa.repo;

import com.example.springdatajpa.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepo extends JpaRepository<Employee,Integer> {

}
