package com.fontys.shiftpayroll.interfaces.repoInterfaces;

import com.fontys.shiftpayroll.domain.Availability;

import java.util.List;
import java.util.UUID;

public interface IAvailabilityRepo {
    Availability save(Availability availability);

    List<Availability> findByEmployeeId(UUID employeeId);
}
