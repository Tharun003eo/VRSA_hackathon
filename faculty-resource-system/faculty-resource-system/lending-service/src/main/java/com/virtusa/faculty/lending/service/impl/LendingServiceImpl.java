package com.virtusa.faculty.lending.service.impl;

import com.virtusa.faculty.lending.entity.Issue;
import com.virtusa.faculty.lending.entity.IssueState;
import com.virtusa.faculty.lending.exception.IssueNotFoundException;
import com.virtusa.faculty.lending.repository.IssueRepository;
import com.virtusa.faculty.lending.service.LendingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LendingServiceImpl implements LendingService {

    private final IssueRepository issueRepository;

    private static final int ISSUE_DURATION_DAYS = 14;

    // issueBook
    @Override
    public Issue issueBook(Long userId, Long bookId) {

        Issue issue = Issue.builder()
                .userId(userId)
                .bookId(bookId)
                .state(IssueState.ISSUED)
                .issuedAt(LocalDateTime.now())
                .dueDate(LocalDateTime.now().plusDays(ISSUE_DURATION_DAYS))
                .build();

        return issueRepository.save(issue);
    }

    // returnBook
    @Override
    public Issue returnBook(Long issueId) {

        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(() -> new IssueNotFoundException(issueId));

        issue.setState(IssueState.RETURNED);
        issue.setReturnedAt(LocalDateTime.now());

        return issueRepository.save(issue);
    }

    // renewBook
    @Override
    public Issue renewBook(Long issueId) {

        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(() -> new IssueNotFoundException(issueId));

        issue.setDueDate(issue.getDueDate().plusDays(ISSUE_DURATION_DAYS));

        return issueRepository.save(issue);
    }

    // bulkIssueBooks
    @Override
    public List<Issue> bulkIssueBooks(Long userId, List<Long> bookIds) {

        List<Issue> issues = bookIds.stream()
                .map(bookId -> Issue.builder()
                        .userId(userId)
                        .bookId(bookId)
                        .state(IssueState.ISSUED)
                        .issuedAt(LocalDateTime.now())
                        .dueDate(LocalDateTime.now().plusDays(ISSUE_DURATION_DAYS))
                        .build())
                .toList();

        return issueRepository.saveAll(issues);
    }

    // getIssuedBooksByUser
    @Override
    public List<Issue> getIssuedBooksByUser(Long userId) {
        return issueRepository.findByUserId(userId);
    }

    // getIssueHistory
    @Override
    public List<Issue> getIssueHistory() {
        return issueRepository.findAll();
    }

    // getDueDates
    @Override
    public List<Issue> getDueDates() {
        return issueRepository.findByState(IssueState.ISSUED);
    }
}