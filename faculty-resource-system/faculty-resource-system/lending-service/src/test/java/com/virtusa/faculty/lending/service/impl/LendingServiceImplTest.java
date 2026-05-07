package com.virtusa.faculty.lending.service.impl;

import com.virtusa.faculty.lending.entity.Issue;
import com.virtusa.faculty.lending.entity.IssueState;
import com.virtusa.faculty.lending.exception.IssueNotFoundException;
import com.virtusa.faculty.lending.repository.IssueRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LendingServiceImplTest {

    @Mock
    private IssueRepository repository;

    @InjectMocks
    private LendingServiceImpl service;

    @Test
    void testIssueBook() {
        when(repository.save(any())).thenAnswer(i -> i.getArgument(0));

        Issue result = service.issueBook(1L, 10L);

        assertEquals(IssueState.ISSUED, result.getState());
        assertNotNull(result.getDueDate());
    }

    @Test
    void testReturnBook() {
        Issue issue = Issue.builder().id(1L).state(IssueState.ISSUED).build();

        when(repository.findById(1L)).thenReturn(Optional.of(issue));
        when(repository.save(any())).thenReturn(issue);

        Issue result = service.returnBook(1L);

        assertEquals(IssueState.RETURNED, result.getState());
    }

    @Test
    void testReturnBookNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IssueNotFoundException.class,
                () -> service.returnBook(1L));
    }

    @Test
    void testRenewBook() {
        Issue issue = Issue.builder()
                .id(1L)
                .dueDate(LocalDateTime.now())
                .build();

        when(repository.findById(1L)).thenReturn(Optional.of(issue));
        when(repository.save(any())).thenReturn(issue);

        Issue result = service.renewBook(1L);

        assertNotNull(result.getDueDate());
    }

    @Test
    void testBulkIssueBooks() {
        when(repository.saveAll(any())).thenAnswer(i -> i.getArgument(0));

        List<Issue> result = service.bulkIssueBooks(1L, List.of(10L, 20L));

        assertEquals(2, result.size());
    }

    @Test
    void testGetIssuedBooksByUser() {
        when(repository.findByUserId(1L))
                .thenReturn(List.of(new Issue(), new Issue()));

        List<Issue> result = service.getIssuedBooksByUser(1L);

        assertEquals(2, result.size());
    }

    @Test
    void testGetIssueHistory() {
        when(repository.findAll()).thenReturn(List.of(new Issue()));

        List<Issue> result = service.getIssueHistory();

        assertEquals(1, result.size());
    }

    @Test
    void testGetDueDates() {
        when(repository.findByState(IssueState.ISSUED))
                .thenReturn(List.of(new Issue()));

        List<Issue> result = service.getDueDates();

        assertEquals(1, result.size());
    }
}