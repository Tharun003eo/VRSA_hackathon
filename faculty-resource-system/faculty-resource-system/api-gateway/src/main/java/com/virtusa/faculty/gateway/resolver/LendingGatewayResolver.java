package com.virtusa.faculty.gateway.resolver;

import com.virtusa.faculty.lending.entity.Issue;
import com.virtusa.faculty.lending.service.LendingService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class LendingGatewayResolver {
    private final LendingService lendingService;

    // ----------- Queries -----------

    // getIssuedBooksByUser
    @QueryMapping
    public List<Issue> getIssuedBooksByUser(@Argument Long userId) {
        return lendingService.getIssuedBooksByUser(userId);
    }

    // getIssueHistory
    @QueryMapping
    public List<Issue> getIssueHistory() {
        return lendingService.getIssueHistory();
    }

    // getDueDates
    @QueryMapping
    public List<Issue> getDueDates() {
        return lendingService.getDueDates();
    }

    // ----------- Mutations -----------

    // issueBook
    @MutationMapping
    public Issue issueBook(@Argument Long userId, @Argument Long bookId) {
        return lendingService.issueBook(userId, bookId);
    }

    // returnBook
    @MutationMapping
    public Issue returnBook(@Argument Long issueId) {
        return lendingService.returnBook(issueId);
    }

    // renewBook
    @MutationMapping
    public Issue renewBook(@Argument Long issueId) {
        return lendingService.renewBook(issueId);
    }

    // bulkIssueBooks
    @MutationMapping
    public List<Issue> bulkIssueBooks(@Argument Long userId, @Argument List<Long> bookIds) {
        return lendingService.bulkIssueBooks(userId, bookIds);
    }
}
