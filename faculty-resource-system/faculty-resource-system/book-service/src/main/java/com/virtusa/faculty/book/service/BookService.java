package com.virtusa.faculty.book.service;

import com.virtusa.faculty.book.entity.Book;

import java.util.List;

public interface BookService {

    // createBook
    // Used to create a new book entry in DRAFT state by a faculty
    Book createBook(Book book);

    // updateBook
    // Used to modify book details (only allowed if not PUBLISHED)
    Book updateBook(Long id, Book updatedBook);

    // deleteBook
    // Used to remove a book (typically allowed only in DRAFT/REVIEW stages)
    void deleteBook(Long id);

    // publishBook
    // Used to move book from REVIEW → PUBLISHED after approval
    Book publishBook(Long id);

    // archiveBook
    // Used to mark a book as ARCHIVED (no longer active/usable)
    Book archiveBook(Long id);

    // versionBook
    // Used to create a new version of an existing book (for updates after publishing)
    Book versionBook(Long id, Book newVersion);

    // getBookById
    // Fetch a single book by its unique ID
    Book getBookById(Long id);

    // searchBooks
    // Search books by keyword (title-based for now, can extend later)
    List<Book> searchBooks(String keyword);

    // getBooksByFaculty
    // Retrieve all books created by a specific faculty (authorId)
    List<Book> getBooksByFaculty(String authorId);

    // getBookVersions
    // Retrieve all versions of a book using same ISBN
    List<Book> getBookVersions(String isbn);
}