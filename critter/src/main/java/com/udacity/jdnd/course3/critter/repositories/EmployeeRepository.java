package com.udacity.jdnd.course3.critter.repositories;

import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;


public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // Find employee by name so we can check if the user already exists in the database
    Optional<Employee> findByName(String name);

    // Find employees by availability
    List<Employee> findByDaysAvailableContaining(DayOfWeek dayOfWeek);


}
