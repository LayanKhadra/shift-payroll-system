package com.fontys.shiftpayroll.dto;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record SetAvailabilityRequest (
    DayOfWeek day,
    LocalTime startTime,
    LocalTime endTime
) {

    public SetAvailabilityRequest {
        if (day == null || startTime == null || endTime == null) {
            throw new IllegalArgumentException("day, startTime and endTime are required");
        }
    }
}