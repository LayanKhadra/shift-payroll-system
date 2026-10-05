package com.fontys.shiftpayroll.service;

import com.fontys.shiftpayroll.persistance.entities.AvailabilityEntity;
import com.fontys.shiftpayroll.persistance.entities.EmployeeEntity;
import com.fontys.shiftpayroll.dto.SetAvailabilityRequest;
import com.fontys.shiftpayroll.persistance.AvailabilityJpaRepository;
import com.fontys.shiftpayroll.persistance.EmployeeJpaRepository;
import com.fontys.shiftpayroll.service.impl.AvailabilityServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

class AvailabilityServiceTest {

    private AvailabilityJpaRepository iAvailabilityRepository;
    private EmployeeJpaRepository iEmployeeRepository;
    private AvailabilityServiceImpl availabilityService;

    @BeforeEach
    void setUp() {
        iAvailabilityRepository = mock(AvailabilityJpaRepository.class);
        iEmployeeRepository = mock(EmployeeJpaRepository.class);
        availabilityService = new AvailabilityServiceImpl(iAvailabilityRepository, iEmployeeRepository);
    }

    @Test
    void setAvailability_savesSuccessfully_whenTimesAreValid() {
        UUID employeeId = UUID.randomUUID();
        EmployeeEntity employee = new EmployeeEntity("Sarah Okonkwo", new BigDecimal("18.50"));

        when(iEmployeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        when(iAvailabilityRepository.save(any(AvailabilityEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        SetAvailabilityRequest request = new SetAvailabilityRequest(
                DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(17, 0)
        );

        AvailabilityEntity result = availabilityService.setAvailability(employeeId, request);

        assertEquals(DayOfWeek.MONDAY, result.getDay());
        assertEquals(LocalTime.of(9, 0), result.getStartTime());
        assertEquals(LocalTime.of(17, 0), result.getEndTime());
        verify(iAvailabilityRepository, times(1)).save(any(AvailabilityEntity.class));
    }

    @Test
    void setAvailability_throwsException_whenEndTimeNotAfterStartTime() {
        UUID employeeId = UUID.randomUUID();

        SetAvailabilityRequest request = new SetAvailabilityRequest(
                DayOfWeek.MONDAY, LocalTime.of(17, 0), LocalTime.of(9, 0)
        );

        assertThrows(IllegalArgumentException.class, () ->
                availabilityService.setAvailability(employeeId, request));

        verifyNoInteractions(iAvailabilityRepository);
    }

    @Test
    void setAvailability_throwsException_whenEmployeeNotFound() {
        UUID employeeId = UUID.randomUUID();
        when(iEmployeeRepository.findById(employeeId)).thenReturn(Optional.empty());

        SetAvailabilityRequest request = new SetAvailabilityRequest(
                DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(17, 0)
        );

        assertThrows(NoSuchElementException.class, () ->
                availabilityService.setAvailability(employeeId, request));
    }
}
