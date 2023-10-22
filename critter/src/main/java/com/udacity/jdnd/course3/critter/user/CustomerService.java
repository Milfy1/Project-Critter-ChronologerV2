package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.dto.DtoConversions;
import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerService {

    @Autowired
    private CustomerRepo customerRepo;
    @Autowired
    private PetRepo petRepo;
    @Autowired
    private DtoConversions dtoConversions;



    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
    Customer customer = dtoConversions.CustomerDTOtoCustomer(customerDTO);
//    List<Pet> pets = petRepo.findAllByCustomerId(customer.getId());
//    customer.setPets(pets);
    customerRepo.save(customer);
    return dtoConversions.CustomertoCustomerDTO(customer);
    }



    public List<CustomerDTO> getAll() {
      return customerRepo.findAll().stream().map(dtoConversions::CustomertoCustomerDTO).collect(Collectors.toList());
    }

    public CustomerDTO getOwnerByPet(long petId) {
        Pet pet = petRepo.getOne(petId);
       Customer customer = pet.getCustomer();
       return dtoConversions.CustomertoCustomerDTO(customer);
    }
}
