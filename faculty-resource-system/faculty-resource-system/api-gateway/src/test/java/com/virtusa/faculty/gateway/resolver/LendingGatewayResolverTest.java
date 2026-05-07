package com.virtusa.faculty.gateway.resolver;

import com.virtusa.faculty.lending.entity.Issue;
import com.virtusa.faculty.lending.service.LendingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LendingGatewayResolverTest {

    @Mock
    private LendingService lendingService;

    @InjectMocks
    private LendingGatewayResolver resolver;

    @Test
    void testGetIssuedBooksByUser() {
        when(lendingService.getIssuedBooksByUser(1L))
                .thenReturn(List.of(new Issue(), new Issue()));

        List<Issue> result = resolver.getIssuedBooksByUser(1L);

        assertEquals(2, result.size());
    }

    @Test
    void testGetIssueHistory() {
        when(lendingService.getIssueHistory())
                .thenReturn(List.of(new Issue()));

        List<Issue> result = resolver.getIssueHistory();

        assertEquals(1, result.size());
    }

    @Test
    void testGetDueDates() {
        when(lendingService.getDueDates())
                .thenReturn(List.of(new Issue(), new Issue(), new Issue()));

        List<Issue> result = resolver.getDueDates();

        assertEquals(3, result.size());
    }

    @Test
    void testIssueBook() {
        Issue issue = Issue.builder().id(1L).build();

        when(lendingService.issueBook(1L, 10L)).thenReturn(issue);

        Issue result = resolver.issueBook(1L, 10L);

        assertEquals(1L, result.getId());
    }

    @Test
    void testReturnBook() {
        Issue issue = Issue.builder().id(1L).build();

        when(lendingService.returnBook(1L)).thenReturn(issue);

        Issue result = resolver.returnBook(1L);

        assertEquals(1L, result.getId());
    }

    @Test
    void testRenewBook() {
        Issue issue = Issue.builder().id(1L).build();

        when(lendingService.renewBook(1L)).thenReturn(issue);

        Issue result = resolver.renewBook(1L);

        assertEquals(1L, result.getId());
    }

    @Test
    void testBulkIssueBooks() {
        when(lendingService.bulkIssueBooks(eq(1L), any()))
                .thenReturn(List.of(new Issue(), new Issue()));

        List<Issue> result = resolver.bulkIssueBooks(1L, List.of(10L, 20L));

        assertEquals(2, result.size());
    }
}