package com.backendintranet.dto.response;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
public class ReservationResponse {
    private String id;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String bookedById;
    private String bookedBy;
    private String purpose;
    private Integer attendees;
    private String room;
    private String status;
}