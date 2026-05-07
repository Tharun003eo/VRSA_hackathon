package com.virtusa.faculty.workflow.service.impl;

import com.virtusa.faculty.workflow.entity.Review;
import com.virtusa.faculty.workflow.entity.ReviewStatus;
import com.virtusa.faculty.workflow.exception.ReviewNotFoundException;
import com.virtusa.faculty.workflow.repository.ReviewRepository;
import com.virtusa.faculty.workflow.service.WorkflowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkflowServiceImpl implements WorkflowService {

    private final ReviewRepository reviewRepository;

    // submitBookForReview
    @Override
    public Review submitBookForReview(Long bookId, Long reviewerId) {

        Review review = Review.builder()
                .bookId(bookId)
                .reviewerId(reviewerId)
                .status(ReviewStatus.PENDING)
                .reviewedAt(LocalDateTime.now())
                .build();

        return reviewRepository.save(review);
    }

    // approveBook
    @Override
    public Review approveBook(Long reviewId) {

        Review review = getReviewStatus(reviewId);
        review.setStatus(ReviewStatus.APPROVED);
        review.setReviewedAt(LocalDateTime.now());

        return reviewRepository.save(review);
    }

    // rejectBook
    @Override
    public Review rejectBook(Long reviewId, String comments) {

        Review review = getReviewStatus(reviewId);
        review.setStatus(ReviewStatus.REJECTED);
        review.setComments(comments);
        review.setReviewedAt(LocalDateTime.now());

        return reviewRepository.save(review);
    }

    // requestChanges
    @Override
    public Review requestChanges(Long reviewId, String comments) {

        Review review = getReviewStatus(reviewId);
        review.setStatus(ReviewStatus.CHANGES_REQUESTED);
        review.setComments(comments);
        review.setReviewedAt(LocalDateTime.now());

        return reviewRepository.save(review);
    }

    // getReviewStatus
    @Override
    public Review getReviewStatus(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException(reviewId));
    }

    // getReviewHistory
    @Override
    public List<Review> getReviewHistory(Long bookId) {
        return reviewRepository.findByBookId(bookId);
    }
}