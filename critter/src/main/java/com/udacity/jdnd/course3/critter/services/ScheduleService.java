package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.entities.Schedule;
import com.udacity.jdnd.course3.critter.repositories.ScheduleRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final EmployeeService employeeService;
    private final PetService petService;

    public ScheduleService(ScheduleRepository scheduleRepository,EmployeeService employeeService,PetService petService) {

        this.scheduleRepository = scheduleRepository;
        this.employeeService= employeeService;
        this.petService=petService;
    }

    @Transactional
    public Schedule saveSchedule(Schedule schedule,List<Long> employeeIds, List<Long> petIds) {
        List<Employee> employees = employeeIds.stream().map(employeeService::getEmployeeById).toList();
        List<Pet> pets =petIds.stream().map(petService::getPetById).collect(Collectors.toList());
        schedule.setEmployees(employees);
        schedule.setPets(pets);
        return scheduleRepository.save(schedule);
    }

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> getSchedulesForPet(Long petId) {
        return scheduleRepository.findByPetsId(petId);
    }

    public List<Schedule> getSchedulesForEmployee(Long employeeId) {
        return scheduleRepository.findByEmployeesId(employeeId);
    }

    public List<Schedule> getSchedulesForCustomer(Long customerId) {
        return petService.getPetsByOwnerId(customerId).stream()
                .flatMap(pet -> getSchedulesForPet(pet.getId()).stream())
                .distinct()
                .toList();
    }
}
