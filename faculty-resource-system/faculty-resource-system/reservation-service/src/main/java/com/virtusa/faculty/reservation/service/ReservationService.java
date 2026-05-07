package com.virtusa.faculty.reservation.service;

import com.virtusa.faculty.reservation.entity.Reservation;

import java.util.List;

public interface ReservationService {

    // reserveBook
    Reservation reserveBook(Long userId, Long bookId);

    // cancelReservation
    void cancelReservation(Long reservationId);

    // allocateReservedBook
    Reservation allocateReservedBook(Long bookId);

    // getReservationQueue
    List<Reservation> getReservationQueue(Long bookId);

    // getUserReservations
    List<Reservation> getUserReservations(Long userId);
}