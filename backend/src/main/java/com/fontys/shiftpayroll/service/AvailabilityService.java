package com.fontys.shiftpayroll.service;

import com.fontys.shiftpayroll.domain.Availability;
import com.fontys.shiftpayroll.interfaces.repoInterfaces.IAvailabilityRepo;
import com.fontys.shiftpayroll.interfaces.repoInterfaces.IEmployeeRepo;

import com.fontys.shiftpayroll.interfaces.serviceInterfaces.IAvailabilityService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class AvailabilityService implements IAvailabilityService {
    private final IAvailabilityRepo availabilityRepo;
    private final IEmployeeRepo employeeRepo;

    public AvailabilityService(IAvailabilityRepo availabilityRepo, IEmployeeRepo employeeRepo) {
        this.availabilityRepo = availabilityRepo;
        this.employeeRepo = employeeRepo;
    }

    @Override
    public Availability setAvailability(Availability availability) {
        if (!availability.getEndTime().isAfter(availability.getStartTime())) {
            throw new IllegalArgumentException("End time must be after start time");
        }

        employeeRepo. findById(availability.getEmployeeId())
                .orElseThrow(() -> new NoSuchElementException("Employee not found: " + availability.getEmployeeId()));

        return availabilityRepo.save(availability);
    }

    @Override
    public List<Availability> getAvailabilityForEmployee(UUID employeeId) {
        return availabilityRepo.findByEmployeeId(employeeId);
    }
}
