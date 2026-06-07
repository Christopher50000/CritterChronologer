package com.udacity.jdnd.course3.critter;

import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.entities.Schedule;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.user.CustomerDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DtoUtils {


    public static EmployeeDTO entityToDTO(Employee employee) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setName(employee.getName());
        dto.setId(employee.getId());
        dto.setSkills(employee.getSkills());
        dto.setDaysAvailable(employee.getDaysAvailable());
        return dto;
    }

    public static Employee dtoToEntity(EmployeeDTO dto) {
        Employee employee = new Employee();
        employee.setName(dto.getName());
        employee.setId(dto.getId());
        employee.setSkills(dto.getSkills());
        employee.setDaysAvailable(dto.getDaysAvailable());
        return employee;
    }

    public static CustomerDTO entityToDTO(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        dto.setName(customer.getName());
        dto.setId(customer.getId());
        dto.setPhoneNumber(customer.getPhoneNumber());
        if (customer.getPets() != null) {
            List<Long> petIds = customer.getPets().stream()
                    .map(Pet::getId)
                    .collect(Collectors.toList());
            dto.setPetIds(petIds);
        }
        return dto;
    }

    public static Customer dtoToEntity(CustomerDTO dto) {
        Customer customer = new Customer();
        customer.setName(dto.getName());
        customer.setId(dto.getId());
        customer.setPhoneNumber(dto.getPhoneNumber());
        return customer;
    }

    public static PetDTO entityToDTO(Pet pet) {
        PetDTO dto = new PetDTO();
        dto.setId(pet.getId());
        dto.setType(pet.getType());
        dto.setName(pet.getName());
        dto.setOwnerId(pet.getCustomer() != null ? pet.getCustomer().getId() : null);
        dto.setBirthDate(pet.getBirthDate());
        dto.setNotes(pet.getNotes());
        return dto;
    }

    public static Pet dtoToEntity(PetDTO dto) {
        Pet pet = new Pet();
        pet.setType(dto.getType());
        pet.setName(dto.getName());
        pet.setBirthDate(dto.getBirthDate());
        pet.setNotes(dto.getNotes());
        // Create a customer placeholder with the ownerId if provided
        if (dto.getOwnerId() != 0) {
            Customer customer = new Customer();
            customer.setId(dto.getOwnerId());
            pet.setCustomer(customer);
        }
        return pet;
    }

    public static ScheduleDTO entityToDTO(Schedule schedule) {
        ScheduleDTO dto = new ScheduleDTO();

        dto.setActivities(schedule.getActivities());

        if (schedule.getEmployees() != null) {
            List<Long> employeeIds = schedule.getEmployees().stream()
                    .map(Employee::getId)
                    .collect(Collectors.toList());
            dto.setEmployeeIds(employeeIds);
        }
        
        if (schedule.getPets() != null) {
            List<Long> petIds = schedule.getPets().stream()
                    .map(Pet::getId)
                    .collect(Collectors.toList());
            dto.setPetIds(petIds);
        }

        dto.setDate(schedule.getDate());
        
        return dto;
    }

    public static Schedule dtoToEntity(ScheduleDTO dto) {
        Schedule schedule = new Schedule();
        schedule.setDate(dto.getDate());
        schedule.setActivities(dto.getActivities());
        return schedule;
    }
}
