package com.virtusa.faculty.gateway.resolver;

import com.virtusa.faculty.reservation.entity.Reservation;
import com.virtusa.faculty.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ReservationGatewayResolver {
    private final ReservationService reservationService;

    // ----------- Queries -----------

    // getReservationQueue
    @QueryMapping
    public List<Reservation> getReservationQueue(@Argument Long bookId) {
        return reservationService.getReservationQueue(bookId);
    }

    // getUserReservations
    @QueryMapping
    public List<Reservation> getUserReservations(@Argument Long userId) {
        return reservationService.getUserReservations(userId);
    }

    // ----------- Mutations -----------

    // reserveBook
    @MutationMapping
    public Reservation reserveBook(@Argument Long userId, @Argument Long bookId) {
        return reservationService.reserveBook(userId, bookId);
    }

    // cancelReservation
    @MutationMapping
    public Boolean cancelReservation(@Argument Long reservationId) {
        reservationService.cancelReservation(reservationId);
        return true;
    }

    // allocateReservedBook
    @MutationMapping
    public Reservation allocateReservedBook(@Argument Long bookId) {
        return reservationService.allocateReservedBook(bookId);
    }
}
