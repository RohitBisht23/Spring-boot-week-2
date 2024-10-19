package com.week_two.SpringBootWebWeekTwo.SpringBootWebWeekTwo.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="employee")
public class EmployeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String email;

    private Integer age;
    private String role;

    private Integer salary;

    private LocalDate dateOfJoin;

    @JsonProperty("isActive")
    private boolean isActive;

}
