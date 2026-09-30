package com.fontys.shiftpayroll.dto;


import com.fontys.shiftpayroll.domain.Availability;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.UUID;

public record AvailabilityResponse (
        UUID id,
        UUID employeeId,
        DayOfWeek day,
        LocalTime startTime,
        LocalTime endTime
) {
    public static AvailabilityResponse from(Availability availability) {
        return new AvailabilityResponse(
                availability.getId(),
                availability.getEmployee().getId(),
                availability.getDay(),
                availability.getStartTime(),
                availability.getEndTime()
        );
    }
}
