package com.virtusa.faculty.workflow.service.impl;

import com.virtusa.faculty.workflow.entity.Review;
import com.virtusa.faculty.workflow.entity.ReviewStatus;
import com.virtusa.faculty.workflow.exception.ReviewNotFoundException;
import com.virtusa.faculty.workflow.repository.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WorkflowServiceImplTest {

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private WorkflowServiceImpl workflowService;

    @Test
    void testSubmitBookForReview() {
        when(reviewRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        Review result = workflowService.submitBookForReview(1L, 2L);

        assertEquals(ReviewStatus.PENDING, result.getStatus());
    }

    @Test
    void testApproveBook() {
        Review review = Review.builder().id(1L).status(ReviewStatus.PENDING).build();

        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));
        when(reviewRepository.save(any())).thenReturn(review);

        Review result = workflowService.approveBook(1L);

        assertEquals(ReviewStatus.APPROVED, result.getStatus());
    }

    @Test
    void testRejectBook() {
        Review review = Review.builder().id(1L).build();

        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));
        when(reviewRepository.save(any())).thenReturn(review);

        Review result = workflowService.rejectBook(1L, "bad");

        assertEquals(ReviewStatus.REJECTED, result.getStatus());
        assertEquals("bad", result.getComments());
    }

    @Test
    void testRequestChanges() {
        Review review = Review.builder().id(1L).build();

        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));
        when(reviewRepository.save(any())).thenReturn(review);

        Review result = workflowService.requestChanges(1L, "fix");

        assertEquals(ReviewStatus.CHANGES_REQUESTED, result.getStatus());
    }

    @Test
    void testGetReviewStatusNotFound() {
        when(reviewRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ReviewNotFoundException.class,
                () -> workflowService.getReviewStatus(1L));
    }

    @Test
    void testGetReviewHistory() {
        when(reviewRepository.findByBookId(1L))
                .thenReturn(List.of(new Review(), new Review()));

        List<Review> result = workflowService.getReviewHistory(1L);

        assertEquals(2, result.size());
    }
}