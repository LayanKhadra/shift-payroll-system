package com.fontys.shiftpayroll.service.impl;

import com.fontys.shiftpayroll.domain.Availability;
import com.fontys.shiftpayroll.domain.Employee;
import com.fontys.shiftpayroll.dto.SetAvailabilityRequest;
import com.fontys.shiftpayroll.interfaces.repoInterfaces.IAvailabilityRepository;
import com.fontys.shiftpayroll.interfaces.repoInterfaces.IEmployeeRepository;
import com.fontys.shiftpayroll.interfaces.serviceInterfaces.IAvailabilityService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class AvailabilityServiceImpl implements IAvailabilityService {
    private final IAvailabilityRepository iAvailabilityRepository;
    private final IEmployeeRepository iEmployeeRepository;

    public AvailabilityServiceImpl(IAvailabilityRepository iAvailabilityRepository,
                                   IEmployeeRepository iEmployeeRepository) {
        this.iAvailabilityRepository = iAvailabilityRepository;
        this.iEmployeeRepository = iEmployeeRepository;
    }

    @Override
    public Availability setAvailability(UUID employeeId, SetAvailabilityRequest request) {
        if (!request.endTime().isAfter(request.startTime())) {
            throw new IllegalArgumentException("End time must be after start time");
        }

        Employee employee = iEmployeeRepository.findById(employeeId)
                .orElseThrow(() -> new NoSuchElementException("Employee not found: " + employeeId));

        Availability availability = new Availability(
                employee, request.day(), request.startTime(), request.endTime()
        );

        return iAvailabilityRepository.save(availability);
    }

    @Override
    public List<Availability> getAvailabilityForEmployee(UUID employeeId) {
        return iAvailabilityRepository.findByEmployeeId(employeeId);
    }
}
