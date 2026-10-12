package com.fontys.shiftpayroll.controller;
import com.fontys.shiftpayroll.dto.EmployeeResponse;
import com.fontys.shiftpayroll.interfaces.serviceInterfaces.IEmployeeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private final IEmployeeService employeeService;

    public EmployeeController(IEmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<EmployeeResponse> getEmployees() {
        return employeeService.getAllEmployees().stream()
                .map(EmployeeResponse::from)
                .toList();
    }
}
