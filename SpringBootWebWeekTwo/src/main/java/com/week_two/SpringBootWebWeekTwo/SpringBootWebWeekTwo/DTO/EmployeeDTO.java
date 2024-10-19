package com.week_two.SpringBootWebWeekTwo.SpringBootWebWeekTwo.DTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.week_two.SpringBootWebWeekTwo.SpringBootWebWeekTwo.Annotations.EmployeeRoleValidation;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
    private Long id;

    @NotBlank(message = "Required field in the Employee cannot be empty: name")
    @Size(min = 3, max = 12)
    private String name;

    @Email(message = "Email should be valid")
    private String email;

    @Max(value=60,message = "The age of employee cannot exceed 60")
    @Min(value=18, message = "The age of employee cannot be lesser than 18")
    private Integer age;

    @NotBlank(message = "Role of employee must be provide")
    //@Pattern(regexp = "^(ADMIN|USER)$", message = "The role of employee can be either USER or ADMIN")
    @EmployeeRoleValidation
    private String role;

    @NotNull(message = "Salary cannot be null")
    @Positive(message = "The salary should be positive")
    private Integer salary;


    @NotNull(message = "Date of join cannot be null")
    @PastOrPresent(message = "The join day should be in past")
    private LocalDate dateOfJoin;
    @JsonProperty("isActive")
    private boolean isActive;

}