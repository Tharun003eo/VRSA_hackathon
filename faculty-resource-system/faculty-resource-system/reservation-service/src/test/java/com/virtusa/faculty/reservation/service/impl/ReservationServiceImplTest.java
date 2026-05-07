package com.virtusa.faculty.reservation.service.impl;

import com.virtusa.faculty.reservation.entity.Reservation;
import com.virtusa.faculty.reservation.exception.ReservationNotFoundException;
import com.virtusa.faculty.reservation.repository.ReservationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceImplTest {

    @Mock
    private ReservationRepository repository;

    @InjectMocks
    private ReservationServiceImpl service;

    @Test
    void testReserveBook() {
        when(repository.findByBookIdOrderByPositionAsc(1L))
                .thenReturn(List.of(new Reservation(), new Reservation()));

        when(repository.save(any())).thenAnswer(i -> i.getArgument(0));

        Reservation result = service.reserveBook(10L, 1L);

        assertEquals(3, result.getPosition());
    }

    @Test
    void testCancelReservation() {
        when(repository.existsById(1L)).thenReturn(true);

        service.cancelReservation(1L);

        verify(repository).deleteById(1L);
    }

    @Test
    void testCancelReservationNotFound() {
        when(repository.existsById(1L)).thenReturn(false);

        assertThrows(ReservationNotFoundException.class,
                () -> service.cancelReservation(1L));
    }

    @Test
    void testAllocateReservedBook() {
        Reservation r1 = Reservation.builder().id(1L).position(1).build();
        Reservation r2 = Reservation.builder().id(2L).position(2).build();

        when(repository.findByBookIdOrderByPositionAsc(1L))
                .thenReturn(List.of(r1, r2));

        Reservation result = service.allocateReservedBook(1L);

        assertEquals(1L, result.getId());
        verify(repository).deleteById(1L);
    }

    @Test
    void testAllocateReservedBookEmpty() {
        when(repository.findByBookIdOrderByPositionAsc(1L))
                .thenReturn(List.of());

        assertThrows(RuntimeException.class,
                () -> service.allocateReservedBook(1L));
    }

    @Test
    void testGetReservationQueue() {
        when(repository.findByBookIdOrderByPositionAsc(1L))
                .thenReturn(List.of(new Reservation()));

        List<Reservation> result = service.getReservationQueue(1L);

        assertEquals(1, result.size());
    }

    @Test
    void testGetUserReservations() {
        when(repository.findByUserId(10L))
                .thenReturn(List.of(new Reservation(), new Reservation()));

        List<Reservation> result = service.getUserReservations(10L);

        assertEquals(2, result.size());
    }
}