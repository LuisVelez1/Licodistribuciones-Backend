package com.backendintranet.repository;

import com.backendintranet.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, String> {

    List<Reservation> findByDateAndStatusNot(LocalDate date, String status);

    @Query("""
        SELECT r FROM Reservation r
        WHERE r.date >= :today AND r.status != 'cancelada'
        ORDER BY r.date ASC, r.startTime ASC
    """)
    List<Reservation> findUpcoming(@Param("today") LocalDate today);

    @Query("""
        SELECT r FROM Reservation r
        WHERE r.room = :room AND r.date = :date
        AND r.status != 'cancelada'
        AND r.startTime < :endTime AND r.endTime > :startTime
    """)
    List<Reservation> findConflicts(
            @Param("room") String room,
            @Param("date") LocalDate date,
            @Param("startTime") java.time.LocalTime startTime,
            @Param("endTime") java.time.LocalTime endTime
    );
}