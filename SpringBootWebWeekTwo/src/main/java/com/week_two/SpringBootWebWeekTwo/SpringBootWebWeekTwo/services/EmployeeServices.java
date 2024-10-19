package com.week_two.SpringBootWebWeekTwo.SpringBootWebWeekTwo.services;

import com.week_two.SpringBootWebWeekTwo.SpringBootWebWeekTwo.DTO.EmployeeDTO;
import com.week_two.SpringBootWebWeekTwo.SpringBootWebWeekTwo.Entities.EmployeeEntity;
import com.week_two.SpringBootWebWeekTwo.SpringBootWebWeekTwo.repositories.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServices {

    private final EmployeeRepository employeeRepository;
    private  final ModelMapper modelMapper;

    public EmployeeServices(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }



    public Optional<EmployeeDTO> getEmployeeById(Long id) {
//        Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(id);
//        return employeeEntity.map(employeeEntity1->modelMapper.map(employeeEntity, EmployeeDTO.class));

        return employeeRepository.findById(id).map(employeeEntity -> modelMapper.map(employeeEntity, EmployeeDTO.class));
    }

    public List<EmployeeDTO> getAllEmployee() {
        List<EmployeeEntity> employeeEntities = employeeRepository.findAll();

        return employeeEntities
                .stream()
                .map(employeeEntity -> modelMapper.map(employeeEntity, EmployeeDTO.class))
                .collect(Collectors.toList());
    }

    public EmployeeDTO createEmployee(EmployeeDTO inputEmployee) {
        EmployeeEntity toSaveEntity = modelMapper.map(inputEmployee, EmployeeEntity.class);
        EmployeeEntity employeeEntity = employeeRepository.save(toSaveEntity);
        return modelMapper.map(employeeEntity, EmployeeDTO.class);
    }

    public EmployeeDTO updateEmployeeByTD(Long empID, EmployeeDTO employeeDTO) {
        //If Employee is not present with this id then simply create it
        EmployeeEntity employeeEntity = modelMapper.map(employeeDTO, EmployeeEntity.class);
        employeeEntity.setId(empID);
        EmployeeEntity savedEmployeeEntity = employeeRepository.save(employeeEntity);
        return modelMapper.map(savedEmployeeEntity, EmployeeDTO.class);
    }

    //Check is this id present of not? This method to make code DRY as possible
    public boolean ifExistEmployeeID(Long empID) {
        return employeeRepository.existsById(empID);
    }

    public boolean deleteEmployeeByID(Long empID) {
        boolean exists = ifExistEmployeeID(empID);
        if(exists) {
            employeeRepository.deleteById(empID);
            return true;
        } else {
            return false;
        }
    }

    public EmployeeDTO updatePartialEmployeeData(Long empID, Map<String, Object> updates) {
        // Check if employee with this id is present or not
        boolean isExists = ifExistEmployeeID(empID);

        if (isExists == false) return null;

        EmployeeEntity employeeEntity = employeeRepository.findById(empID).get();

        updates.forEach((field, value) -> {
            Field fieldToBeUpdated = ReflectionUtils.findField(EmployeeEntity.class, field);
            fieldToBeUpdated.setAccessible(true);
            ReflectionUtils.setField(fieldToBeUpdated, employeeEntity, value);
        });

        EmployeeEntity employeeEntityToBeSaved = employeeRepository.save(employeeEntity);
        return modelMapper.map(employeeEntityToBeSaved, EmployeeDTO.class);
    }

}
