package com.virtusa.faculty.lending.service;

import com.virtusa.faculty.lending.entity.Issue;

import java.util.List;

public interface LendingService {

    // issueBook
    Issue issueBook(Long userId, Long bookId);

    // returnBook
    Issue returnBook(Long issueId);

    // renewBook
    Issue renewBook(Long issueId);

    // bulkIssueBooks
    List<Issue> bulkIssueBooks(Long userId, List<Long> bookIds);

    // getIssuedBooksByUser
    List<Issue> getIssuedBooksByUser(Long userId);

    // getIssueHistory
    List<Issue> getIssueHistory();

    // getDueDates
    List<Issue> getDueDates();
}