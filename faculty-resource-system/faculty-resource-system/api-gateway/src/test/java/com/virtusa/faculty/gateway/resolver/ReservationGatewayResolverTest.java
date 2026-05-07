package com.virtusa.faculty.gateway.resolver;

import com.virtusa.faculty.reservation.entity.Reservation;
import com.virtusa.faculty.reservation.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationGatewayResolverTest {

    @Mock
    private ReservationService reservationService;

    @InjectMocks
    private ReservationGatewayResolver resolver;

    @Test
    void testGetReservationQueue() {
        when(reservationService.getReservationQueue(1L))
                .thenReturn(List.of(new Reservation(), new Reservation()));

        List<Reservation> result = resolver.getReservationQueue(1L);

        assertEquals(2, result.size());
    }

    @Test
    void testGetUserReservations() {
        when(reservationService.getUserReservations(10L))
                .thenReturn(List.of(new Reservation()));

        List<Reservation> result = resolver.getUserReservations(10L);

        assertEquals(1, result.size());
    }

    @Test
    void testReserveBook() {
        Reservation reservation = Reservation.builder().id(1L).build();

        when(reservationService.reserveBook(10L, 1L)).thenReturn(reservation);

        Reservation result = resolver.reserveBook(10L, 1L);

        assertEquals(1L, result.getId());
    }

    @Test
    void testCancelReservation() {
        Boolean result = resolver.cancelReservation(1L);

        assertTrue(result);
        verify(reservationService).cancelReservation(1L);
    }

    @Test
    void testAllocateReservedBook() {
        Reservation reservation = Reservation.builder().id(1L).build();

        when(reservationService.allocateReservedBook(1L)).thenReturn(reservation);

        Reservation result = resolver.allocateReservedBook(1L);

        assertEquals(1L, result.getId());
    }
}