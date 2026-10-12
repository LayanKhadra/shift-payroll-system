package com.fontys.shiftpayroll.persistance.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "employee")
public class EmployeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal hourlyRate;

    protected EmployeeEntity() {
        // required by JPA
    }

    public EmployeeEntity(String name, BigDecimal hourlyRate) {
        this.name = name;
        this.hourlyRate = hourlyRate;
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
