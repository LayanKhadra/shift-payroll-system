package com.fontys.shiftpayroll.service;

import com.fontys.shiftpayroll.domain.Availability;
import com.fontys.shiftpayroll.domain.Employee;
import com.fontys.shiftpayroll.dto.SetAvailabilityRequest;
import com.fontys.shiftpayroll.repository.AvailabilityRepository;
import com.fontys.shiftpayroll.repository.EmployeeRepository;
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

    private AvailabilityRepository availabilityRepository;
    private EmployeeRepository employeeRepository;
    private AvailabilityServiceImpl availabilityService;

    @BeforeEach
    void setUp() {
        availabilityRepository = mock(AvailabilityRepository.class);
        employeeRepository = mock(EmployeeRepository.class);
        availabilityService = new AvailabilityServiceImpl(availabilityRepository, employeeRepository);
    }

    @Test
    void setAvailability_savesSuccessfully_whenTimesAreValid() {
        UUID employeeId = UUID.randomUUID();
        Employee employee = new Employee("Sarah Okonkwo", new BigDecimal("18.50"));

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        when(availabilityRepository.save(any(Availability.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        SetAvailabilityRequest request = new SetAvailabilityRequest(
                DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(17, 0)
        );

        Availability result = availabilityService.setAvailability(employeeId, request);

        assertEquals(DayOfWeek.MONDAY, result.getDay());
        assertEquals(LocalTime.of(9, 0), result.getStartTime());
        assertEquals(LocalTime.of(17, 0), result.getEndTime());
        verify(availabilityRepository, times(1)).save(any(Availability.class));
    }

    @Test
    void setAvailability_throwsException_whenEndTimeNotAfterStartTime() {
        UUID employeeId = UUID.randomUUID();

        SetAvailabilityRequest request = new SetAvailabilityRequest(
                DayOfWeek.MONDAY, LocalTime.of(17, 0), LocalTime.of(9, 0)
        );

        assertThrows(IllegalArgumentException.class, () ->
                availabilityService.setAvailability(employeeId, request));

        verifyNoInteractions(availabilityRepository);
    }

    @Test
    void setAvailability_throwsException_whenEmployeeNotFound() {
        UUID employeeId = UUID.randomUUID();
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

        SetAvailabilityRequest request = new SetAvailabilityRequest(
                DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(17, 0)
        );

        assertThrows(NoSuchElementException.class, () ->
                availabilityService.setAvailability(employeeId, request));
    }
}
