package com.fontys.shiftpayroll.persistance;

import com.fontys.shiftpayroll.domain.Employee;
import com.fontys.shiftpayroll.interfaces.repoInterfaces.IEmployeeRepo;
import com.fontys.shiftpayroll.persistance.entities.EmployeeEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class EmployeeRepo implements IEmployeeRepo {
    private final EmployeeJpaRepository employeeJpaRepository;

    public EmployeeRepo(EmployeeJpaRepository employeeJpaRepository) {
        this.employeeJpaRepository = employeeJpaRepository;
    }

    @Override
    public Optional<Employee> findById(UUID id) {
        // 1 fetch from the database (returns an entity)
        // 2 map to domain
        return employeeJpaRepository.findById(id).map(EmployeeRepo::toDomain);
    }

    private static Employee toDomain(EmployeeEntity entity) {
        return new Employee(entity.getId(), entity.getName(), entity.getHourlyRate());
    }

}
