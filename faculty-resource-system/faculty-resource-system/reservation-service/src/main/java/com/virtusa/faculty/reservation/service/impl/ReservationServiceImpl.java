package com.virtusa.faculty.reservation.service.impl;

import com.virtusa.faculty.reservation.entity.Reservation;
import com.virtusa.faculty.reservation.exception.ReservationNotFoundException;
import com.virtusa.faculty.reservation.repository.ReservationRepository;
import com.virtusa.faculty.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    private static final int EXPIRY_HOURS = 24;

    // reserveBook
    @Override
    public Reservation reserveBook(Long userId, Long bookId) {

        List<Reservation> queue = reservationRepository.findByBookIdOrderByPositionAsc(bookId);

        int position = queue.size() + 1;

        Reservation reservation = Reservation.builder()
                .userId(userId)
                .bookId(bookId)
                .position(position)
                .reservedAt(LocalDateTime.now())
                .expiryAt(LocalDateTime.now().plusHours(EXPIRY_HOURS))
                .build();

        return reservationRepository.save(reservation);
    }

    // cancelReservation
    @Override
    public void cancelReservation(Long reservationId) {

        if (!reservationRepository.existsById(reservationId)) {
            throw new ReservationNotFoundException(reservationId);
        }

        reservationRepository.deleteById(reservationId);
    }

    // allocateReservedBook
    @Override
    public Reservation allocateReservedBook(Long bookId) {

        List<Reservation> queue = reservationRepository.findByBookIdOrderByPositionAsc(bookId);

        if (queue.isEmpty()) {
            throw new RuntimeException("No reservations available");
        }

        Reservation first = queue.get(0);
        reservationRepository.deleteById(first.getId());

        return first;
    }

    // getReservationQueue
    @Override
    public List<Reservation> getReservationQueue(Long bookId) {
        return reservationRepository.findByBookIdOrderByPositionAsc(bookId);
    }

    // getUserReservations
    @Override
    public List<Reservation> getUserReservations(Long userId) {
        return reservationRepository.findByUserId(userId);
    }
}