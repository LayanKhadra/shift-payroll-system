package com.fontys.shiftpayroll.service;

import com.fontys.shiftpayroll.domain.Availability;
import com.fontys.shiftpayroll.domain.Employee;
import com.fontys.shiftpayroll.dto.SetAvailabilityRequest;
import com.fontys.shiftpayroll.interfaces.repoInterfaces.IAvailabilityRepository;
import com.fontys.shiftpayroll.interfaces.repoInterfaces.IEmployeeRepository;
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

    private IAvailabilityRepository iAvailabilityRepository;
    private IEmployeeRepository iEmployeeRepository;
    private AvailabilityServiceImpl availabilityService;

    @BeforeEach
    void setUp() {
        iAvailabilityRepository = mock(IAvailabilityRepository.class);
        iEmployeeRepository = mock(IEmployeeRepository.class);
        availabilityService = new AvailabilityServiceImpl(iAvailabilityRepository, iEmployeeRepository);
    }

    @Test
    void setAvailability_savesSuccessfully_whenTimesAreValid() {
        UUID employeeId = UUID.randomUUID();
        Employee employee = new Employee("Sarah Okonkwo", new BigDecimal("18.50"));

        when(iEmployeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        when(iAvailabilityRepository.save(any(Availability.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        SetAvailabilityRequest request = new SetAvailabilityRequest(
                DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(17, 0)
        );

        Availability result = availabilityService.setAvailability(employeeId, request);

        assertEquals(DayOfWeek.MONDAY, result.getDay());
        assertEquals(LocalTime.of(9, 0), result.getStartTime());
        assertEquals(LocalTime.of(17, 0), result.getEndTime());
        verify(iAvailabilityRepository, times(1)).save(any(Availability.class));
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
