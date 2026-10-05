package com.fontys.shiftpayroll.interfaces.repoInterfaces;

import com.fontys.shiftpayroll.domain.Availability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface IAvailabilityRepository extends JpaRepository <Availability, UUID> {
    List<Availability> findByEmployeeId(UUID employeeId);
}
