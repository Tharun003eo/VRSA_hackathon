package com.virtusa.faculty.lending.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "issues")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long bookId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IssueState state; /*REQUESTED,
    ISSUED,
    RETURNED,
    OVERDUE*/

    private LocalDateTime issuedAt;
    private LocalDateTime dueDate;
    private LocalDateTime returnedAt;
}