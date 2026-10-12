package com.fontys.shiftpayroll.interfaces.serviceInterfaces;
import com.fontys.shiftpayroll.domain.Employee;

import java.util.List;;

public interface IEmployeeService {
    List<Employee> getAllEmployees();
}
