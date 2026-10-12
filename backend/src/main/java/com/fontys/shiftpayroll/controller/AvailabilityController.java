package com.fontys.shiftpayroll.controller;

import com.fontys.shiftpayroll.domain.Availability;
import com.fontys.shiftpayroll.dto.AvailabilityResponse;
import com.fontys.shiftpayroll.dto.SetAvailabilityRequest;
import com.fontys.shiftpayroll.interfaces.serviceInterfaces.IAvailabilityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/employees/{employeeId}/availability")
public class AvailabilityController {

    private final IAvailabilityService iAvailabilityService;

    public AvailabilityController(IAvailabilityService iAvailabilityService) {
        this.iAvailabilityService = iAvailabilityService;
    }

    @PostMapping
    public ResponseEntity<AvailabilityResponse> setAvailability(
            @PathVariable UUID employeeId,
            @RequestBody SetAvailabilityRequest request) {
        Availability availability  = new Availability(
                employeeId, request.day(), request.startTime(), request.endTime());
        Availability saved = iAvailabilityService.setAvailability(availability);
        return ResponseEntity.status(HttpStatus.CREATED).body(AvailabilityResponse.from(saved));
    }

    @GetMapping
    public List<AvailabilityResponse> getAvailability(@PathVariable UUID employeeId) {
        return iAvailabilityService.getAvailabilityForEmployee(employeeId).stream()
                .map(AvailabilityResponse::from)
                .toList();
    }
}
