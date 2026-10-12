package com.fontys.shiftpayroll.interfaces.repoInterfaces;

import com.fontys.shiftpayroll.domain.Employee;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

public interface IEmployeeRepo {
    Optional<Employee> findById(UUID id);
    List<Employee> findAll();
}
