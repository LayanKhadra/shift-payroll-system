package com.fontys.shiftpayroll.interfaces.repoInterfaces;

import com.fontys.shiftpayroll.domain.Employee;
import java.util.Optional;
import java.util.UUID;

public interface IEmployeeRepo {
    Optional<Employee> findById(UUID id);
}
