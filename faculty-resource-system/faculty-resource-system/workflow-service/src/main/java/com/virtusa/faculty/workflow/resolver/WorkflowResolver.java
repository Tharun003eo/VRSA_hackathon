package com.virtusa.faculty.workflow.resolver;

import com.virtusa.faculty.workflow.entity.Review;
import com.virtusa.faculty.workflow.service.WorkflowService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.List;

//@Controller
@RequiredArgsConstructor
public class WorkflowResolver {

    private final WorkflowService workflowService;

    // ----------- Queries -----------

    // getReviewStatus
    @QueryMapping
    public Review getReviewStatus(@Argument Long reviewId) {
        return workflowService.getReviewStatus(reviewId);
    }

    // getReviewHistory
    @QueryMapping
    public List<Review> getReviewHistory(@Argument Long bookId) {
        return workflowService.getReviewHistory(bookId);
    }

    // ----------- Mutations -----------

    // submitBookForReview
    @MutationMapping
    public Review submitBookForReview(@Argument Long bookId, @Argument Long reviewerId) {
        return workflowService.submitBookForReview(bookId, reviewerId);
    }

    // approveBook
    @MutationMapping
    public Review approveBook(@Argument Long reviewId) {
        return workflowService.approveBook(reviewId);
    }

    // rejectBook
    @MutationMapping
    public Review rejectBook(@Argument Long reviewId, @Argument String comments) {
        return workflowService.rejectBook(reviewId, comments);
    }

    // requestChanges
    @MutationMapping
    public Review requestChanges(@Argument Long reviewId, @Argument String comments) {
        return workflowService.requestChanges(reviewId, comments);
    }
}