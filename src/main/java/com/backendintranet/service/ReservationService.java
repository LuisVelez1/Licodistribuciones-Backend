package com.backendintranet.service;

import com.backendintranet.dto.request.ReservationRequest;
import com.backendintranet.dto.response.ReservationResponse;
import java.time.LocalDate;
import java.util.List;

public interface ReservationService {
    ReservationResponse create(ReservationRequest dto, String userId);
    List<ReservationResponse> getByDate(LocalDate date);
    List<ReservationResponse> getUpcoming();
    ReservationResponse cancel(String id, String userId);
}