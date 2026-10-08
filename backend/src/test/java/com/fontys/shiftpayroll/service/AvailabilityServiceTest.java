package com.fontys.shiftpayroll.service;

import com.fontys.shiftpayroll.domain.Availability;
import com.fontys.shiftpayroll.domain.Employee;
import com.fontys.shiftpayroll.interfaces.repoInterfaces.IAvailabilityRepo;
import com.fontys.shiftpayroll.interfaces.repoInterfaces.IEmployeeRepo;
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

    private IAvailabilityRepo availabilityRepo;
    private IEmployeeRepo employeeRepo;
    private AvailabilityService availabilityService;

    @BeforeEach
    void setUp() {
        availabilityRepo = mock(IAvailabilityRepo.class);
        employeeRepo = mock(IEmployeeRepo.class);
        availabilityService = new AvailabilityService(availabilityRepo, employeeRepo);
    }

    @Test
    void setAvailability_savesSuccessfully_whenTimesAreValid() {
        UUID employeeId = UUID.randomUUID();
        givenEmployeeExists(employeeId);
        when(availabilityRepo.save(any(Availability.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));


        Availability result = availabilityService.setAvailability(
                new Availability(employeeId, DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(17, 0)));

        assertEquals(DayOfWeek.MONDAY, result.getDay());
        assertEquals(LocalTime.of(9, 0), result.getStartTime());
        assertEquals(LocalTime.of(17, 0), result.getEndTime());
        verify(availabilityRepo, times(1)).save(any(Availability.class));
    }

    @Test
    void setAvailability_throwsException_whenEndTimeNotAfterStartTime() {
        UUID employeeId = UUID.randomUUID();

        assertThrows(IllegalArgumentException.class, () ->
                availabilityService.setAvailability(
                        new Availability(employeeId, DayOfWeek.MONDAY, LocalTime.of(17, 0), LocalTime.of(9, 0))));


        verifyNoInteractions(availabilityRepo);
    }
    @Test
    void setAvailability_throwsException_whenStartEqualsEnd() {
        UUID employeeId = UUID.randomUUID();

        assertThrows(IllegalArgumentException.class, () ->
                availabilityService.setAvailability(
                        new Availability(employeeId, DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(9, 0))));

        verifyNoInteractions(availabilityRepo);
    }

    @Test
    void setAvailability_saves_whenEndIsOneMinuteAfterStart() {
        UUID employeeId = UUID.randomUUID();
        givenEmployeeExists(employeeId);
        when(availabilityRepo.save(any(Availability.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Availability result = availabilityService.setAvailability(
                new Availability(employeeId, DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(9, 1)));

        assertEquals(LocalTime.of(9, 1), result.getEndTime());
    }

    @Test
    void setAvailability_throwsException_whenEmployeeNotFound() {
        UUID employeeId = UUID.randomUUID();
        when(employeeRepo.findById(employeeId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () ->
                availabilityService.setAvailability(
                        new Availability(employeeId, DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(17, 0))));
    }

    private void givenEmployeeExists(UUID employeeId) {
        when(employeeRepo.findById(employeeId)).thenReturn(
                Optional.of(new Employee(employeeId, "Sarah Okonkwo", new BigDecimal("18.50"))));
    }
}
