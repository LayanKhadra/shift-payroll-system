package com.fontys.shiftpayroll.persistance;

import com.fontys.shiftpayroll.persistance.entities.EmployeeEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * Development only sample data. The MVP has no screen for registering
 * employees so a few are created on first startup. Their ids are logged
 * on every startup so they can be used when trying the API.
 */

@Component
public class DataSeeder implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(DataSeeder.class);
    private final EmployeeJpaRepository employeeJpaRepository;

    public DataSeeder(EmployeeJpaRepository employeeJpaRepository) {
        this.employeeJpaRepository = employeeJpaRepository;
    }

    @Override
    public void run(String... args) {
        if (employeeJpaRepository.count() == 0) {
            employeeJpaRepository.saveAll(List.of(
                    new EmployeeEntity("Sarah Okonkwo", new BigDecimal("18.50")),
                    new EmployeeEntity("Marcus Chen", new BigDecimal("20.00")),
                    new EmployeeEntity("Priya Patel", new BigDecimal("22.50"))
            ));
        }

        employeeJpaRepository.findAll()
                .forEach(e -> log.info("Employee: {} -> {}", e.getName(), e.getId()));
    }
}
