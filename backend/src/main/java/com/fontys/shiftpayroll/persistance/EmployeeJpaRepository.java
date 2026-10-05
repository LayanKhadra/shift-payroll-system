package com.fontys.shiftpayroll.persistance;

import com.fontys.shiftpayroll.persistance.entities.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EmployeeJpaRepository extends JpaRepository<EmployeeEntity,  UUID> {
}
