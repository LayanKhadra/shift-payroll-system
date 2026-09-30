package com.fontys.shiftpayroll.service.impl;

import com.fontys.shiftpayroll.domain.Availability;
import com.fontys.shiftpayroll.domain.Employee;
import com.fontys.shiftpayroll.dto.SetAvailabilityRequest;
import com.fontys.shiftpayroll.repository.AvailabilityRepository;
import com.fontys.shiftpayroll.repository.EmployeeRepository;
import com.fontys.shiftpayroll.service.AvailabilityService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class AvailabilityServiceImpl implements AvailabilityService {
    private final AvailabilityRepository availabilityRepository;
    private final EmployeeRepository employeeRepository;

    public AvailabilityServiceImpl(AvailabilityRepository availabilityRepository,
                                   EmployeeRepository employeeRepository) {
        this.availabilityRepository = availabilityRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Availability setAvailability(UUID employeeId, SetAvailabilityRequest request) {
        if (!request.endTime().isAfter(request.startTime())) {
            throw new IllegalArgumentException("End time must be after start time");
        }

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new NoSuchElementException("Employee not found: " + employeeId));

        Availability availability = new Availability(
                employee, request.day(), request.startTime(), request.endTime()
        );

        return availabilityRepository.save(availability);
    }

    @Override
    public List<Availability> getAvailabilityForEmployee(UUID employeeId) {
        return availabilityRepository.findByEmployeeId(employeeId);
    }
}
