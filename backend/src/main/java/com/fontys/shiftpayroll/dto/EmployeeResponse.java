package com.fontys.shiftpayroll.dto;

import com.fontys.shiftpayroll.domain.Employee;

import java.util.UUID;

public record EmployeeResponse(UUID id, String name) {

    public static EmployeeResponse from(Employee employee) {
        return new EmployeeResponse(employee.getId(), employee.getName());
    }
}
