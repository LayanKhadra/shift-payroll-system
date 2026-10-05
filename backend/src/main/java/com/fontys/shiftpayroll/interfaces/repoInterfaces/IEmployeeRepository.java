package com.fontys.shiftpayroll.interfaces.repoInterfaces;

import com.fontys.shiftpayroll.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IEmployeeRepository extends JpaRepository<Employee ,  UUID> {
}
