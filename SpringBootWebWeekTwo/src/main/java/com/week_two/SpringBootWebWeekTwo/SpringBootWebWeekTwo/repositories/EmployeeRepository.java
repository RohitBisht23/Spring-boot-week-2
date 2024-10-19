package com.week_two.SpringBootWebWeekTwo.SpringBootWebWeekTwo.repositories;

import com.week_two.SpringBootWebWeekTwo.SpringBootWebWeekTwo.Entities.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {

}
