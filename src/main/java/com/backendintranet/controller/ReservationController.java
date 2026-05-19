package com.backendintranet.controller;

import com.backendintranet.dto.request.ReservationRequest;
import com.backendintranet.dto.response.ReservationResponse;
import com.backendintranet.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationResponse> create(
            @RequestBody ReservationRequest dto,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        return ResponseEntity.ok(reservationService.create(dto, userDetails.getUsername()));
    }

    @GetMapping("/by-date")
    public ResponseEntity<List<ReservationResponse>> getByDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        return ResponseEntity.ok(reservationService.getByDate(date));
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<ReservationResponse>> getUpcoming() {
        return ResponseEntity.ok(reservationService.getUpcoming());
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<ReservationResponse> cancel(
            @PathVariable String id,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        return ResponseEntity.ok(reservationService.cancel(id, userDetails.getUsername()));
    }
}