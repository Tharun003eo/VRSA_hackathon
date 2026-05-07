package com.virtusa.faculty.lending.repository;

import com.virtusa.faculty.lending.entity.Issue;
import com.virtusa.faculty.lending.entity.IssueState;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class IssueRepositoryTest {

    @Autowired
    private IssueRepository repository;

    private Issue create(Long userId, Long bookId, IssueState state) {
        return Issue.builder()
                .userId(userId)
                .bookId(bookId)
                .state(state)
                .build();
    }

    @Test
    void testFindByUserId() {
        repository.save(create(1L, 10L, IssueState.ISSUED));
        repository.save(create(1L, 20L, IssueState.RETURNED));

        List<Issue> result = repository.findByUserId(1L);

        assertEquals(2, result.size());
    }

    @Test
    void testFindByState() {
        repository.save(create(1L, 10L, IssueState.ISSUED));

        List<Issue> result = repository.findByState(IssueState.ISSUED);

        assertEquals(1, result.size());
    }
}