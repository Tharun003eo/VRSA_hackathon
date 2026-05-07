package com.virtusa.faculty.workflow.repository;

import com.virtusa.faculty.workflow.entity.Review;
import com.virtusa.faculty.workflow.entity.ReviewStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByBookId(Long bookId);

    List<Review> findByStatus(ReviewStatus status);
}