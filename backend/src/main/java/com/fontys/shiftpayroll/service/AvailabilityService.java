package com.fontys.shiftpayroll.service;

import com.fontys.shiftpayroll.domain.Availability;
import com.fontys.shiftpayroll.dto.SetAvailabilityRequest;

import java.util.List;
import java.util.UUID;

public interface AvailabilityService {

    Availability setAvailability(UUID employeeId, SetAvailabilityRequest request);

    List<Availability> getAvailabilityForEmployee(UUID employeeId);
}
