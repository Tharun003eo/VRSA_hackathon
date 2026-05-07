package com.virtusa.faculty.lending.repository;

import com.virtusa.faculty.lending.entity.Issue;
import com.virtusa.faculty.lending.entity.IssueState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IssueRepository extends JpaRepository<Issue, Long> {

    List<Issue> findByUserId(Long userId);

    List<Issue> findByState(IssueState state);
}