package com.fontys.shiftpayroll.interfaces.serviceInterfaces;

import com.fontys.shiftpayroll.persistance.entities.AvailabilityEntity;
import com.fontys.shiftpayroll.dto.SetAvailabilityRequest;

import java.util.List;
import java.util.UUID;

public interface IAvailabilityService {

    AvailabilityEntity setAvailability(UUID employeeId, SetAvailabilityRequest request);

    List<AvailabilityEntity> getAvailabilityForEmployee(UUID employeeId);
}
