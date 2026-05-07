package com.virtusa.faculty.book.dto;

import lombok.Data;

@Data
public class BookInput {
    private String isbn;
    private String title;
    private String authorId;
    private String version;
    private String description;
}