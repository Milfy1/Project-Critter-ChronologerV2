package com.udacity.jdnd.course3.critter.dto;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.pet.PetRepo;
import com.udacity.jdnd.course3.critter.schedule.Schedule;
import com.udacity.jdnd.course3.critter.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.user.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DtoConversions {
    private final CustomerRepo customerRepo;
    private final EmployeeRepo employeeRepo;
    private final PetRepo petRepo;


    public DtoConversions(CustomerRepo customerRepo, EmployeeRepo employeeRepo, PetRepo petRepo) {
        this.customerRepo = customerRepo;
        this.employeeRepo = employeeRepo;
        this.petRepo = petRepo;
    }

    public Pet getPetMapper(PetDTO petDTO) {
        Pet pet = new Pet();
        pet.setId(petDTO.getId());
        pet.setNotes(petDTO.getNotes());
        pet.setName(petDTO.getName());
        pet.setBirthDate(petDTO.getBirthDate());
        pet.setType(petDTO.getType());
        pet.setCustomer(customerRepo.getOne(petDTO.getOwnerId()));
        return pet;
    }
    public PetDTO getPetDTOMapper(Pet pet) {
        PetDTO petDTO = new PetDTO();
        petDTO.setId(pet.getId());
        petDTO.setNotes(pet.getNotes());
        petDTO.setName(pet.getName());
        petDTO.setBirthDate(pet.getBirthDate());
        petDTO.setType(pet.getType());
        petDTO.setOwnerId(pet.getCustomer().getId());
        return petDTO;
    }

    public Schedule getSchedule(ScheduleDTO scheduleDTO){
        Schedule schedule = new Schedule();
        schedule.setId(scheduleDTO.getId());
        schedule.setDate(scheduleDTO.getDate());
        schedule.setPets(scheduleDTO.getPetIds().stream().map(id -> petRepo.findById(id).get()).collect(Collectors.toList()));
        schedule.setEmployees(scheduleDTO.getEmployeeIds().stream().map(id -> employeeRepo.findById(id).get()).collect(Collectors.toList()));
        schedule.setActivities(scheduleDTO.getActivities());
        return schedule;
    }
    public ScheduleDTO getScheduleDTO(Schedule schedule){
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setId(schedule.getId());
        scheduleDTO.setActivities(schedule.getActivities());
        scheduleDTO.setDate(schedule.getDate());
        scheduleDTO.setPetIds(schedule.getPets().stream().map(pet -> pet.getId()).collect(Collectors.toList()));
        scheduleDTO.setEmployeeIds(schedule.getEmployees().stream().map(employee -> employee.getId()).collect(Collectors.toList()));
        return scheduleDTO;
    }

    public Customer CustomerDTOtoCustomer(CustomerDTO customerDTO) {
        List<Long> petIds = customerDTO.getPetIds();
        Customer customer = new Customer();
        customer.setId(customerDTO.getId());
        customer.setName(customerDTO.getName());
        customer.setNotes(customerDTO.getNotes());
        customer.setPhoneNumber(customerDTO.getPhoneNumber());
        if (petIds != null && !petIds.isEmpty()) {
            customer.setPets(petIds.stream().map(id -> petRepo.findById(id).get()).collect(Collectors.toList()));
        }else{
            customer.setPets(new ArrayList<>());
        }
        return customer;
    }
    public CustomerDTO CustomertoCustomerDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setName(customer.getName());
        customerDTO.setNotes(customer.getNotes());
        customerDTO.setPhoneNumber(customer.getPhoneNumber());
        customerDTO.setPetIds(customer.getPets().stream().map(Pet::getId).collect(Collectors.toList()));
        return customerDTO;
    }
    public EmployeeDTO mapToEmployeeDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId((employee.getId()));
        employeeDTO.setName(employee.getName());
        employeeDTO.setSkills(employee.getSkills());
        employeeDTO.setDaysAvailable(employee.getDaysAvailable());
        return employeeDTO;
    }
    public Employee mapTOEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setId((employeeDTO.getId()));
        employee.setName(employeeDTO.getName());
        employee.setSkills(employeeDTO.getSkills());
        employee.setDaysAvailable(employeeDTO.getDaysAvailable());
        return employee;
    }
}
