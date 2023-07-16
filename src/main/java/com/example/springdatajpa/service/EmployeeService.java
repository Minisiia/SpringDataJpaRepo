package com.example.springdatajpa.service;

import com.example.springdatajpa.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EmployeeService {
    List<Employee> findAll();

    Page<Employee> getEmployees(int pageNumber, int pageSize, String sortBy);
}
