package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.services.PetService;
import com.udacity.jdnd.course3.critter.DtoUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        return DtoUtils.entityToDTO(petService.savePet(DtoUtils.dtoToEntity(petDTO)));
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        return DtoUtils.entityToDTO(petService.getPetById(petId));
    }

    @GetMapping
    public List<PetDTO> getPets(){
        return petService.getAllPets().stream()
                .map(DtoUtils::entityToDTO)
                .toList();
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        return petService.getPetsByOwnerId(ownerId).stream()
                .map(DtoUtils::entityToDTO)
                .toList();
    }
}
