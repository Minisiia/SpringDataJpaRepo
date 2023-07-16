package com.example.springdatajpa.repo;

import com.example.springdatajpa.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EmployeeRepo extends JpaRepository<Employee,Integer> {

    @Modifying
    @Query("DELETE FROM Employee e WHERE e.name like %?1%")
    void deleteByPartOfName(String partName);

    void deleteByNameContaining(String partialName);


}
