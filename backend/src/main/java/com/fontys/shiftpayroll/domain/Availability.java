package com.fontys.shiftpayroll.domain;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.UUID;

public class Availability {
    private final UUID id;
    private final UUID employeeId;
    private final DayOfWeek day;
    private final LocalTime startTime;
    private final LocalTime endTime;

    public Availability(UUID id, UUID employeeId, DayOfWeek day, LocalTime startTime, LocalTime endTime) {
        this.id = id;
        this.employeeId = employeeId;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Availability(UUID employeeId, DayOfWeek day, LocalTime startTime, LocalTime endTime) {
        this(null, employeeId, day, startTime, endTime);
    }

    public UUID getId() {
        return id;
    }

    public UUID getEmployeeId() {
        return employeeId;
    }

    public DayOfWeek getDay() {
        return day;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }
}
