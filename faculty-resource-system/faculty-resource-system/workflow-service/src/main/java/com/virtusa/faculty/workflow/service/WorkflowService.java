package com.virtusa.faculty.workflow.service;

import com.virtusa.faculty.workflow.entity.Review;

import java.util.List;

public interface WorkflowService {

    // submitBookForReview
    Review submitBookForReview(Long bookId, Long reviewerId);

    // approveBook
    Review approveBook(Long reviewId);

    // rejectBook
    Review rejectBook(Long reviewId, String comments);

    // requestChanges
    Review requestChanges(Long reviewId, String comments);

    // getReviewStatus
    Review getReviewStatus(Long reviewId);

    // getReviewHistory
    List<Review> getReviewHistory(Long bookId);
}