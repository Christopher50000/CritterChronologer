package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.adviceController.exceptions.EntityAlreadyExistsException;
import com.udacity.jdnd.course3.critter.adviceController.exceptions.EntityNotFoundException;
import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.repositories.EmployeeRepository;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService (EmployeeRepository employeeRepository){
        this.employeeRepository=employeeRepository;
    }

    @Transactional
    public Employee saveEmployee(Employee employee) {
        // Check if employee with same name already exists
        if (employee.getName() != null) {
            Optional<Employee> existingEmployee = employeeRepository.findByName(employee.getName());
            if (existingEmployee.isPresent() && 
                (employee.getId() == null || !existingEmployee.get().getId().equals(employee.getId()))) {
                throw new EntityAlreadyExistsException("Employee with name '" + employee.getName() + "' already exists");
            }
        }
        
        return employeeRepository.save(employee);
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee with ID " + id + " not found"));
    }


    @Transactional
    public void setEmployeeAvailability(Set<DayOfWeek> daysAvailable,long employeeId){
        Employee employee = getEmployeeById(employeeId);
        employee.setDaysAvailable(daysAvailable);
        employeeRepository.save(employee);
    }


    public List<Employee> findEmployeesByService(Set<EmployeeSkill> skills, DayOfWeek dayOfWeek){
        List<Employee> availableEmployees = employeeRepository.findByDaysAvailableContaining(dayOfWeek);
        return availableEmployees.stream()
                .filter(employee -> employee.getSkills() != null && employee.getSkills().containsAll(skills))
                .toList();
    }

    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

}
