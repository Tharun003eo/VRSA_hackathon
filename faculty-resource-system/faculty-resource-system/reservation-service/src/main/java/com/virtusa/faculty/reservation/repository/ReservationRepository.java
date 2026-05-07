package com.virtusa.faculty.reservation.repository;

import com.virtusa.faculty.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByBookIdOrderByPositionAsc(Long bookId);

    List<Reservation> findByUserId(Long userId);
}