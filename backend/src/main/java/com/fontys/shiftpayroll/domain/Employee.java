package com.fontys.shiftpayroll.domain;

import java.math.BigDecimal;
import java.util.UUID;

public class Employee {

    private final UUID id;
    private final String name;
    private final BigDecimal hourlyRate;

    public Employee(UUID id, String name, BigDecimal hourlyRate) {
        this.id = id;
        this.name = name;
        this.hourlyRate = hourlyRate;
    }

    public Employee(String name, BigDecimal hourlyRate) {
        this(null, name, hourlyRate);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getHourlyRate() {
        return hourlyRate;
    }
}