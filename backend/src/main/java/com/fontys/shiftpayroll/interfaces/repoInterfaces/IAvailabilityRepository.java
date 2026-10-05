package com.fontys.shiftpayroll.interfaces.repoInterfaces;

import com.fontys.shiftpayroll.persistance.entities.AvailabilityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface IAvailabilityRepository extends JpaRepository <AvailabilityEntity, UUID> {
    List<AvailabilityEntity> findByEmployeeId(UUID employeeId);
}
