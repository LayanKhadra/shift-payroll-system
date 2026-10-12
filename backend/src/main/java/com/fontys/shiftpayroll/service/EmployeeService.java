package com.fontys.shiftpayroll.service;

import com.fontys.shiftpayroll.domain.Employee;
import com.fontys.shiftpayroll.interfaces.repoInterfaces.IEmployeeRepo;
import com.fontys.shiftpayroll.interfaces.serviceInterfaces.IEmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService implements IEmployeeService {
    private final IEmployeeRepo employeeRepo;

    public EmployeeService(IEmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepo.findAll();
    }
}
