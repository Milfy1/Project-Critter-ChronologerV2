package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.dto.DtoConversions;
import com.udacity.jdnd.course3.critter.pet.PetRepo;
import com.udacity.jdnd.course3.critter.user.EmployeeRepo;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ScheduleService {
    private final ScheduleRepo scheduleRepo;
    private final EmployeeRepo employeeRepo;
    private final DtoConversions dtoConversions;



    private final PetRepo petRepo;

    public ScheduleService(ScheduleRepo scheduleRepo, EmployeeRepo employeeRepo, DtoConversions dtoConversions, PetRepo petRepo) {
        this.scheduleRepo = scheduleRepo;
        this.employeeRepo = employeeRepo;
        this.dtoConversions = dtoConversions;
        this.petRepo = petRepo;
    }

    public ScheduleDTO createSchedule(ScheduleDTO scheduleDTO) {
        Schedule schedule = dtoConversions.getSchedule(scheduleDTO);
        return dtoConversions.getScheduleDTO(scheduleRepo.save(schedule));
    }

    public List<ScheduleDTO> getAll() {
      return scheduleRepo.findAll().stream().map(dtoConversions::getScheduleDTO).collect(Collectors.toList());
    }

    public List<ScheduleDTO> getScheduleForPet(long petId) {
        List<Schedule> schedules =  scheduleRepo.getAllByPetsContains(petRepo.getOne(petId));
        return schedules.stream().map(dtoConversions::getScheduleDTO).collect(Collectors.toList());
    }

    public List<ScheduleDTO> getScheduleForEmployee(long employeeId) {
        List<Schedule> schedules =  scheduleRepo.getAllByEmployeesContains(employeeRepo.getOne(employeeId));
        return schedules.stream().map(dtoConversions::getScheduleDTO).collect(Collectors.toList());
    }

    public List<ScheduleDTO> getScheduleForCustomer(long customerId) {
       List<Schedule> schedules = scheduleRepo.getAllByPetsIn(petRepo.findAllByCustomerId(customerId));
        return schedules.stream().map(dtoConversions::getScheduleDTO).collect(Collectors.toList());
    }


}
