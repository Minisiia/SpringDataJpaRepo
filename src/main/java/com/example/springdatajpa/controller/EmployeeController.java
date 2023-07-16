package com.example.springdatajpa.controller;

import com.example.springdatajpa.model.Employee;
import com.example.springdatajpa.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/all")
    public Iterable<Employee> getAllUsers() {
        // This returns a JSON or XML with the users
        return employeeService.findAll();
    }

            //http://localhost:8080/paging?pageNumber=0&pageSize=2&sortBy=name
    @GetMapping("/paging")
    public Iterable<Employee> get1pageAllUsers(@RequestParam int pageNumber,
                                                             @RequestParam int pageSize,
                                                             @RequestParam String sortBy) {
        // This returns a JSON or XML with the users
        System.out.println(employeeService.getEmployees(pageNumber,pageSize, sortBy));
        return employeeService.getEmployees(pageNumber,pageSize, sortBy);
    }
            //http://localhost:8080/batch-save
//  [
//    {
//        "name": "John Lol",
//            "position": "Manager",
//            "phone": "123-456-1111"
//    },
//    {
//        "name": "Jane Lol",
//            "position": "Developer",
//            "phone": "987-654-1111"
//    }
//]
    @PostMapping("/batch-save")
    public void batchSaveEmployees(@RequestBody List<Employee> employees) {
        employeeService.performBatchSave(employees);
    }

            //http://localhost:8080/delete?partialName=Lol
    @DeleteMapping("/delete")
    public void deleteEmployeesByPartialName(@RequestParam String partialName) {
        employeeService.deleteByNameContaining(partialName);
    }

            //http://localhost:8080/delete-in-batch
    @DeleteMapping("/delete-in-batch")
    public void deleteBatch(@RequestBody Iterable<Employee> entities) {
        employeeService.deleteInBatch(entities);
    }

            //http://localhost:8080/paging/0/2/name

//        @GetMapping("/paging/{pageNumber}/{pageSize}/{sortBy}")
//    public @ResponseBody Iterable<Employee> get1pageAllUsers(@PathVariable int pageNumber,
//                                                             @PathVariable int pageSize,
//                                                             @PathVariable String sortBy) {
//        // This returns a JSON or XML with the users
//        System.out.println(employeeService.getEmployees(pageNumber,pageSize, sortBy));
//        return employeeService.getEmployees(pageNumber,pageSize, sortBy);


}
