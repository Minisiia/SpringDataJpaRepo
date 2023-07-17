package com.example.springdatajpa.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//import jakarta.persistence.*;
import javax.persistence.*;


@Entity
@Data
@Table(name = "employee")
public class Employee {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
   @Column(name = "name")
    private String name;
    @Column(name = "position")
    private String position;
  @Column(name = "phone")
    private String phone;

}
