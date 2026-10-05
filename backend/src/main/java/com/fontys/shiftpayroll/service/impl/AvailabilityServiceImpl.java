package com.fontys.shiftpayroll.service.impl;

import com.fontys.shiftpayroll.persistance.entities.AvailabilityEntity;
import com.fontys.shiftpayroll.persistance.entities.EmployeeEntity;
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
    public AvailabilityEntity setAvailability(UUID employeeId, SetAvailabilityRequest request) {
        if (!request.endTime().isAfter(request.startTime())) {
            throw new IllegalArgumentException("End time must be after start time");
        }

        EmployeeEntity employee = iEmployeeRepository.findById(employeeId)
                .orElseThrow(() -> new NoSuchElementException("Employee not found: " + employeeId));

        AvailabilityEntity availability = new AvailabilityEntity(
                employee, request.day(), request.startTime(), request.endTime()
        );

        return iAvailabilityRepository.save(availability);
    }

    @Override
    public List<AvailabilityEntity> getAvailabilityForEmployee(UUID employeeId) {
        return iAvailabilityRepository.findByEmployeeId(employeeId);
    }
}
