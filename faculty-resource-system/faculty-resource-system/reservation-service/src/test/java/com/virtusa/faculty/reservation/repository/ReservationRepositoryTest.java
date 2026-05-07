package com.virtusa.faculty.reservation.repository;

import com.virtusa.faculty.reservation.entity.Reservation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ReservationRepositoryTest {

    @Autowired
    private ReservationRepository repository;

    private Reservation create(Long userId, Long bookId, int position) {
        return Reservation.builder()
                .userId(userId)
                .bookId(bookId)
                .position(position)
                .build();
    }

    @Test
    void testFindByBookIdOrder() {
        repository.save(create(1L, 10L, 2));
        repository.save(create(2L, 10L, 1));

        List<Reservation> result =
                repository.findByBookIdOrderByPositionAsc(10L);

        assertEquals(2, result.size());
        assertTrue(result.get(0).getPosition() <= result.get(1).getPosition());
    }

    @Test
    void testFindByUserId() {
        repository.save(create(1L, 10L, 1));
        repository.save(create(1L, 20L, 2));

        List<Reservation> result = repository.findByUserId(1L);

        assertEquals(2, result.size());
    }
}