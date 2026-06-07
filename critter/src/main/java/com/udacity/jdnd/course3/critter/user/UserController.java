package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.DtoUtils;
import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.services.CustomerService;
import com.udacity.jdnd.course3.critter.services.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    
    private final CustomerService customerService;
    private final EmployeeService employeeService;
    
    public UserController(CustomerService customerService, EmployeeService employeeService){
        this.customerService = customerService;
        this.employeeService = employeeService;
    }

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        Customer customer = DtoUtils.dtoToEntity(customerDTO);
        Customer savedCustomer = customerService.saveCustomer(customer);
        return DtoUtils.entityToDTO(savedCustomer);
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        return customerService.getAllCustomers().stream()
                .map(DtoUtils::entityToDTO)
                .toList();
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        Customer customer = customerService.getCustomerByPetId(petId);
        return DtoUtils.entityToDTO(customer);
    }


    @GetMapping("/employee")
    public List<EmployeeDTO> getAllEmployees() {
        return employeeService.getAllEmployees().stream()
                .map(DtoUtils::entityToDTO)
                .toList();
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee newEmployee= DtoUtils.dtoToEntity(employeeDTO);

        newEmployee = employeeService.saveEmployee(newEmployee);

        EmployeeDTO newEmployeeDto= DtoUtils.entityToDTO(newEmployee);

        return newEmployeeDto;
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {

        EmployeeDTO employeeDTO= DtoUtils.entityToDTO(employeeService.getEmployeeById(employeeId));

        return employeeDTO;
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {

        employeeService.setEmployeeAvailability(daysAvailable, employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTORequest){

        DayOfWeek dayOfWeek= DayOfWeek.from(employeeDTORequest.getDate());

        List<EmployeeDTO> employeesForService= employeeService.findEmployeesByService(employeeDTORequest.getSkills(),dayOfWeek).stream().map(DtoUtils::entityToDTO).toList();


        return employeesForService;
    }

}
