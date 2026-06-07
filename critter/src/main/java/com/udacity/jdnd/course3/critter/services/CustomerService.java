package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.adviceController.exceptions.EntityAlreadyExistsException;
import com.udacity.jdnd.course3.critter.adviceController.exceptions.EntityNotFoundException;
import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.repositories.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public Customer saveCustomer(Customer customer) {
        if (customer.getName() != null) {
            Optional<Customer> existingCustomer = customerRepository.findByName(customer.getName());
            if (existingCustomer.isPresent() && 
                (customer.getId() == null || !existingCustomer.get().getId().equals(customer.getId()))) {
                throw new EntityAlreadyExistsException("Customer with name '" + customer.getName() + "' already exists");
            }
        }
        
        return customerRepository.save(customer);
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer with ID " + id + " not found"));
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerByPetId(Long petId) {
        return customerRepository.findByPetsId(petId)
                .orElseThrow(() -> new EntityNotFoundException("Customer with pet ID " + petId + " not found"));
    }
}
