package com.fontys.shiftpayroll.interfaces.serviceInterfaces;

import com.fontys.shiftpayroll.domain.Availability;

import java.util.List;
import java.util.UUID;

public interface IAvailabilityService {

    Availability setAvailability(Availability availability);

    List<Availability> getAvailabilityForEmployee(UUID employeeId);
}
