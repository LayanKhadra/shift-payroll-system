package com.fontys.shiftpayroll.repository;

import com.fontys.shiftpayroll.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<Employee ,  UUID> {
}
