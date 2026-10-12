package com.fontys.shiftpayroll.persistance;

import com.fontys.shiftpayroll.domain.Availability;
import com.fontys.shiftpayroll.interfaces.repoInterfaces.IAvailabilityRepo;
import com.fontys.shiftpayroll.persistance.entities.AvailabilityEntity;
import com.fontys.shiftpayroll.persistance.entities.EmployeeEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Repository
public class AvailabilityRepo implements IAvailabilityRepo {
    private final AvailabilityJpaRepository availabilityJpaRepository;
    private final EmployeeJpaRepository employeeJpaRepository;

    public AvailabilityRepo(AvailabilityJpaRepository availabilityJpaRepository,
                            EmployeeJpaRepository employeeJpaRepository) {
        this.availabilityJpaRepository = availabilityJpaRepository;
        this.employeeJpaRepository = employeeJpaRepository;
    }

    @Override
    public Availability save(Availability availability) {
        // The entity has no way to carry an existing id yet so only new records
        // are supported Fail loudly instead of silently creating a duplicate
        if (availability.getId() != null) {
            throw new UnsupportedOperationException("Updating availability is not supported yet");
        }

        // The entity needs a real EmployeeEntity the domain object only has an id
        EmployeeEntity employee = employeeJpaRepository.findById(availability.getEmployeeId())
                .orElseThrow(() -> new NoSuchElementException(
                        "Employee not found: " + availability.getEmployeeId()));

        AvailabilityEntity entity = new AvailabilityEntity(
                employee, availability.getDay(), availability.getStartTime(), availability.getEndTime());

        return toDomain(availabilityJpaRepository.save(entity));
    }

    @Override
    public List<Availability> findByEmployeeId(UUID employeeId) {
        return availabilityJpaRepository.findByEmployeeId(employeeId).stream()
                .map(AvailabilityRepo::toDomain)
                .toList();
    }

    private static Availability toDomain(AvailabilityEntity entity) {
        return new Availability(
                entity.getId(),
                entity.getEmployee().getId(),
                entity.getDay(),
                entity.getStartTime(),
                entity.getEndTime());
    }
}
