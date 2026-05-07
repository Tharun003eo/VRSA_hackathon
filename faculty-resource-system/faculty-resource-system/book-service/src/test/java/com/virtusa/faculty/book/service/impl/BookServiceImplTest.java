package com.virtusa.faculty.book.service.impl;

import com.virtusa.faculty.book.entity.Book;
import com.virtusa.faculty.book.entity.BookState;
import com.virtusa.faculty.book.exception.DuplicateIsbnException;
import com.virtusa.faculty.book.exception.InvalidBookStateException;
import com.virtusa.faculty.book.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    // createBook success
    @Test
    void testCreateBookSuccess() {
        Book book = Book.builder()
                .isbn("ISBN-1")
                .title("DSA")
                .authorId("1")
                .version("v1")
                .build();

        when(bookRepository.findByIsbn("ISBN-1")).thenReturn(Optional.empty());
        when(bookRepository.save(any(Book.class))).thenAnswer(i -> i.getArgument(0));

        Book result = bookService.createBook(book);

        assertEquals(BookState.DRAFT, result.getState());
        verify(bookRepository).save(book);
    }

    // duplicate ISBN
    @Test
    void testCreateBookDuplicateIsbn() {
        Book book = Book.builder().isbn("ISBN-1").build();

        when(bookRepository.findByIsbn("ISBN-1"))
                .thenReturn(Optional.of(new Book()));

        assertThrows(DuplicateIsbnException.class,
                () -> bookService.createBook(book));
    }

    // updateBook success
    @Test
    void testUpdateBookSuccess() {
        Book existing = Book.builder()
                .id(1L)
                .title("Old")
                .state(BookState.DRAFT)
                .build();

        Book updated = Book.builder()
                .title("New")
                .description("Updated")
                .build();

        when(bookRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(bookRepository.save(any(Book.class))).thenReturn(existing);

        Book result = bookService.updateBook(1L, updated);

        assertEquals("New", result.getTitle());
    }

    // update published book
    @Test
    void testUpdatePublishedBook() {
        Book existing = Book.builder()
                .id(1L)
                .state(BookState.PUBLISHED)
                .build();

        when(bookRepository.findById(1L)).thenReturn(Optional.of(existing));

        assertThrows(InvalidBookStateException.class,
                () -> bookService.updateBook(1L, new Book()));
    }

    // publishBook success
    @Test
    void testPublishBook() {
        Book book = Book.builder()
                .id(1L)
                .state(BookState.REVIEW)
                .build();

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepository.save(any())).thenReturn(book);

        Book result = bookService.publishBook(1L);

        assertEquals(BookState.PUBLISHED, result.getState());
    }

    // publish invalid state
    @Test
    void testPublishInvalidState() {
        Book book = Book.builder()
                .id(1L)
                .state(BookState.DRAFT)
                .build();

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        assertThrows(InvalidBookStateException.class,
                () -> bookService.publishBook(1L));
    }

    // archiveBook
    @Test
    void testArchiveBook() {
        Book book = Book.builder()
                .id(1L)
                .state(BookState.DRAFT)
                .build();

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepository.save(any())).thenReturn(book);

        Book result = bookService.archiveBook(1L);

        assertEquals(BookState.ARCHIVED, result.getState());
    }

    // getBookById
    @Test
    void testGetBookById() {
        Book book = Book.builder().id(1L).build();

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Book result = bookService.getBookById(1L);

        assertNotNull(result);
    }
}