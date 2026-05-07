package com.virtusa.faculty.workflow.repository;

import com.virtusa.faculty.workflow.entity.Review;
import com.virtusa.faculty.workflow.entity.ReviewStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository repository;

    private Review create(Long bookId, ReviewStatus status) {
        return Review.builder()
                .bookId(bookId)
                .reviewerId(1L)
                .status(status)
                .build();
    }

    @Test
    void testFindByBookId() {
        repository.save(create(1L, ReviewStatus.PENDING));
        repository.save(create(1L, ReviewStatus.APPROVED));

        List<Review> result = repository.findByBookId(1L);

        assertEquals(2, result.size());
    }

    @Test
    void testFindByStatus() {
        repository.save(create(1L, ReviewStatus.APPROVED));

        List<Review> result = repository.findByStatus(ReviewStatus.APPROVED);

        assertEquals(1, result.size());
    }
}