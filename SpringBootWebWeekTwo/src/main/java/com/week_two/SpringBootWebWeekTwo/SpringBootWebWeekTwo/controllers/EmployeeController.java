package com.week_two.SpringBootWebWeekTwo.SpringBootWebWeekTwo.controllers;


import com.week_two.SpringBootWebWeekTwo.SpringBootWebWeekTwo.DTO.EmployeeDTO;
import com.week_two.SpringBootWebWeekTwo.SpringBootWebWeekTwo.services.EmployeeServices;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping(path="employee")
public class EmployeeController {

    private final EmployeeServices employeeServices;

    public EmployeeController(EmployeeServices employeeServices) {
        this.employeeServices = employeeServices;
    }

    @GetMapping(path="/yourSecret")
    public String getYourSupperSecret() {
        return "Your secret pass is : HelloRohit@333";
    }

    //1->Data passing with the help of Path variable
    @GetMapping("/{empID}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable(name = "empID") Long id) {
        Optional<EmployeeDTO> employeeDTO = employeeServices.getEmployeeById(id);

        return employeeDTO
                .map(employeeDTO1 -> ResponseEntity.ok(employeeDTO1))
//                .orElse(ResponseEntity.notFound().build()
                .orElseThrow(()->new NoSuchElementException("No employee found")
        );
    }

    //Controller level exception
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleEmployeeNotFound(NoSuchElementException exception) {
        return new ResponseEntity<>("Employee was not found", HttpStatus.NOT_FOUND);
    }

    //2->Data passing with the help of RequestParam
    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployee(@RequestParam(required = false, name="inputAge") Integer age, @RequestParam(required = false) String sortby) {
        return ResponseEntity.ok(employeeServices.getAllEmployee());
    }

    //3->Post mapping
//    @PostMapping
//    public String PostMapping() {
//        return "Hello from post mapping";
//    }

    @PostMapping(path="/createNew")
    public ResponseEntity<EmployeeDTO> getCreateEmployee(@RequestBody @Valid EmployeeDTO inputEmployee) {
        EmployeeDTO savedEmployee = employeeServices.createEmployee(inputEmployee);

        //Below is used to create new status code
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    //Update employee by id
    @PutMapping(path="/{empID}")
    public ResponseEntity<EmployeeDTO> updateEmployeeById(@RequestBody EmployeeDTO employeeDTO, @PathVariable Long empID) {
        EmployeeDTO updatedEmployeeDTO = employeeServices.updateEmployeeByTD(empID, employeeDTO);

        return ResponseEntity.ok(updatedEmployeeDTO);
    }



    //Delete employee by id
    @DeleteMapping(path="/{empID}")
    public ResponseEntity<String> deleteEmployeeByID(@PathVariable Long empID) {
        boolean isDeleted = employeeServices.deleteEmployeeByID(empID);
        if (isDeleted) {
            return ResponseEntity.ok("Data is deleted successfully with id: " + empID);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Employee with id " + empID + " not found.");
        }
    }

    //Patch mapping
    @PatchMapping(path="/{empID}")
    public ResponseEntity<EmployeeDTO> updatePartialEmployeeData(@RequestBody Map<String, Object> updates,@PathVariable Long empID) {
        EmployeeDTO employeeDTO = employeeServices.updatePartialEmployeeData(empID, updates);
        if(employeeDTO == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(employeeDTO);
        }
    }
}
