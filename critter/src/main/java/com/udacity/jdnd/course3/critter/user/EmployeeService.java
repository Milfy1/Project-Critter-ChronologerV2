package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.dto.DtoConversions;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeService {

    private final EmployeeRepo employeeRepo;

    private final DtoConversions dtoConversions;
    public EmployeeService(EmployeeRepo employeeRepo, DtoConversions dtoConversions) {
        this.employeeRepo = employeeRepo;
        this.dtoConversions = dtoConversions;
    }


    public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO) {
      Employee employee = dtoConversions.mapTOEmployee(employeeDTO);
     return dtoConversions.mapToEmployeeDTO(employeeRepo.save(employee));
    }

    public EmployeeDTO getEmployee(long employeeId) {
        return dtoConversions.mapToEmployeeDTO(employeeRepo.getOne(employeeId));
    }

    public void setAvailability(Set<DayOfWeek> daysAvailable, long employeeId) {
       Employee employee = employeeRepo.getOne(employeeId);
       employee.setDaysAvailable(daysAvailable);
       employeeRepo.save(employee);
    }

    public List<EmployeeDTO> findEmployeesForService(EmployeeRequestDTO employeeRequestDTO) {
        List<Employee> employees = employeeRepo.getAllByDaysAvailableContains(employeeRequestDTO.getDate().getDayOfWeek());
        List<EmployeeDTO> employeeDTOS = employees.stream().filter(employee -> employee.getSkills().containsAll(employeeRequestDTO.getSkills())).map(dtoConversions::mapToEmployeeDTO).collect(Collectors.toList());
       return employeeDTOS;
    }
}
