package com.virtusa.faculty.gateway.resolver;

import com.virtusa.faculty.workflow.entity.Review;
import com.virtusa.faculty.workflow.service.WorkflowService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WorkflowGatewayResolverTest {

    @Mock
    private WorkflowService workflowService;

    @InjectMocks
    private WorkflowGatewayResolver resolver;

    @Test
    void testGetReviewStatus() {
        Review review = Review.builder().id(1L).build();

        when(workflowService.getReviewStatus(1L)).thenReturn(review);

        Review result = resolver.getReviewStatus(1L);

        assertEquals(1L, result.getId());
    }

    @Test
    void testGetReviewHistory() {
        when(workflowService.getReviewHistory(1L))
                .thenReturn(List.of(new Review(), new Review()));

        List<Review> result = resolver.getReviewHistory(1L);

        assertEquals(2, result.size());
    }

    @Test
    void testSubmitBookForReview() {
        Review review = Review.builder().id(1L).build();

        when(workflowService.submitBookForReview(1L, 2L)).thenReturn(review);

        Review result = resolver.submitBookForReview(1L, 2L);

        assertEquals(1L, result.getId());
    }

    @Test
    void testApproveBook() {
        when(workflowService.approveBook(1L))
                .thenReturn(Review.builder().id(1L).build());

        Review result = resolver.approveBook(1L);

        assertEquals(1L, result.getId());
    }

    @Test
    void testRejectBook() {
        when(workflowService.rejectBook(1L, "bad"))
                .thenReturn(Review.builder().id(1L).build());

        Review result = resolver.rejectBook(1L, "bad");

        assertEquals(1L, result.getId());
    }

    @Test
    void testRequestChanges() {
        when(workflowService.requestChanges(1L, "fix"))
                .thenReturn(Review.builder().id(1L).build());

        Review result = resolver.requestChanges(1L, "fix");

        assertEquals(1L, result.getId());
    }
}