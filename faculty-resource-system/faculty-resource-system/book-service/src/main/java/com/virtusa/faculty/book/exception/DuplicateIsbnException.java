package com.virtusa.faculty.book.exception;

public class DuplicateIsbnException extends RuntimeException {
    public DuplicateIsbnException(String isbn) {
        super("Book with ISBN already exists: " + isbn);
    }
}