package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.dto.DtoConversions;
import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerRepo;
import com.udacity.jdnd.course3.critter.user.CustomerService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PetService {

    private final PetRepo petRepo;
    private final CustomerRepo customerRepo;

    private final DtoConversions dtoConversions;



    public PetService(PetRepo petRepo, CustomerRepo customerRepo, CustomerService customerService, DtoConversions dtoConversions) {
        this.petRepo = petRepo;
        this.customerRepo = customerRepo;
        this.dtoConversions = dtoConversions;
    }
//the 2 bellow functions are for mapping from and to DTO



    public PetDTO savePet(PetDTO petDTO){
      Pet pet = dtoConversions.getPetMapper(petDTO);
      System.out.println(pet);
      Customer customer = customerRepo.getOne(petDTO.getOwnerId());
      pet.setCustomer(customer);
      pet = petRepo.save(pet);
      customer.addPet(pet);
      customerRepo.save(customer);
       return dtoConversions.getPetDTOMapper(pet);
    }

    public PetDTO getPet(long petId) {
        Pet pet = petRepo.getOne(petId);
        return dtoConversions.getPetDTOMapper(pet);
    }

    public List<PetDTO> getAll() {
       List<PetDTO> pets = petRepo.findAll().stream().map(dtoConversions::getPetDTOMapper).collect(Collectors.toList());
        return pets;
    }

    public List<PetDTO> getPetsByOwner(long ownerId) {
        List<PetDTO> pets = petRepo.findAllByCustomerId(ownerId).stream().map(dtoConversions::getPetDTOMapper).collect(Collectors.toList());
       return pets;
    }
}
