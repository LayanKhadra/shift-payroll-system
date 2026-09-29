package com.fontys.shiftpayroll.repository;

import com.fontys.shiftpayroll.domain.Availability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AvailabilityRepository extends JpaRepository <Availability, UUID> {
    List<Availability> findByEmployeeId(UUID employeeId);
}
