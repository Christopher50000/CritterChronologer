package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.adviceController.exceptions.EntityNotFoundException;
import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.repositories.CustomerRepository;
import com.udacity.jdnd.course3.critter.repositories.PetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PetService {

    private final PetRepository petRepository;
    private final CustomerRepository customerRepository;

    public PetService(PetRepository petRepository, CustomerRepository customerRepository) {
        this.petRepository = petRepository;
        this.customerRepository = customerRepository;
    }

    @Transactional
    public Pet savePet(Pet pet) {
        // If pet has an owner ID set in the DTO, we need to establish the relationship
        // This handles the case where dtoToEntity creates a pet without customer relationship
        if (pet.getCustomer() != null && pet.getCustomer().getId() != null) {
            Customer customer = customerRepository.findById(pet.getCustomer().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Customer with ID " + pet.getCustomer().getId() + " not found"));
            pet.setCustomer(customer);
            customer.getPets().add(pet);
        }
        return petRepository.save(pet);
    }

    public Pet getPetById(Long id) {
        return petRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pet with ID " + id + " not found"));
    }

    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    public List<Pet> getPetsByOwnerId(Long ownerId) {
        Customer customer = customerRepository.findById(ownerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer with ID " + ownerId + " not found"));
        return customer.getPets();
    }
}
