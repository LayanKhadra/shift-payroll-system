package com.fontys.shiftpayroll.interfaces.serviceInterfaces;

import com.fontys.shiftpayroll.domain.Availability;
import com.fontys.shiftpayroll.dto.SetAvailabilityRequest;

import java.util.List;
import java.util.UUID;

public interface IAvailabilityService {

    Availability setAvailability(UUID employeeId, SetAvailabilityRequest request);

    List<Availability> getAvailabilityForEmployee(UUID employeeId);
}
