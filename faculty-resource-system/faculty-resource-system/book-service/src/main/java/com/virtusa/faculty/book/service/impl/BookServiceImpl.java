package com.virtusa.faculty.book.service.impl;

import com.virtusa.faculty.book.entity.Book;
import com.virtusa.faculty.book.entity.BookState;
import com.virtusa.faculty.book.exception.BookNotFoundException;
import com.virtusa.faculty.book.exception.DuplicateIsbnException;
import com.virtusa.faculty.book.exception.InvalidBookStateException;
import com.virtusa.faculty.book.repository.BookRepository;
import com.virtusa.faculty.book.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    // createBook
    @Override
    public Book createBook(Book book) {

        if (bookRepository.findByIsbn(book.getIsbn()).isPresent()) {
            throw new DuplicateIsbnException(book.getIsbn());
        }

        book.setState(BookState.DRAFT);
        book.setCreatedAt(LocalDateTime.now());
        book.setUpdatedAt(LocalDateTime.now());

        return bookRepository.save(book);
    }

    // updateBook
    @Override
    public Book updateBook(Long id, Book updatedBook) {
        Book book = getBookById(id);

        if (book.getState() == BookState.PUBLISHED) {
            throw new InvalidBookStateException("Cannot update published book. Use versioning.");
        }

        book.setTitle(updatedBook.getTitle());
        book.setDescription(updatedBook.getDescription());
        book.setUpdatedAt(LocalDateTime.now());

        return bookRepository.save(book);
    }

    // deleteBook
    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    // publishBook
    @Override
    public Book publishBook(Long id) {
        Book book = getBookById(id);

        if (book.getState() != BookState.REVIEW) {
            throw new InvalidBookStateException("Only books in REVIEW can be published");
        }

        book.setState(BookState.PUBLISHED);
        book.setUpdatedAt(LocalDateTime.now());

        return bookRepository.save(book);
    }

    // archiveBook
    @Override
    public Book archiveBook(Long id) {
        Book book = getBookById(id);

        book.setState(BookState.ARCHIVED);
        book.setUpdatedAt(LocalDateTime.now());

        return bookRepository.save(book);
    }

    // versionBook
    @Override
    public Book versionBook(Long id, Book newVersion) {
        Book existing = getBookById(id);

        Book versioned = Book.builder()
                .isbn(existing.getIsbn())
                .title(newVersion.getTitle())
                .authorId(existing.getAuthorId())
                .version(newVersion.getVersion())
                .state(BookState.DRAFT)
                .description(newVersion.getDescription())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        return bookRepository.save(versioned);
    }

    // getBookById
    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    // searchBooks
    @Override
    public List<Book> searchBooks(String keyword) {
        return bookRepository.searchByTitle(keyword);
    }

    // getBooksByFaculty
    @Override
    public List<Book> getBooksByFaculty(String authorId) {
        return bookRepository.findByAuthorId(authorId);
    }

    // getBookVersions
    @Override
    public List<Book> getBookVersions(String isbn) {
        return bookRepository.findAll()
                .stream()
                .filter(b -> b.getIsbn().equals(isbn))
                .toList();
    }
}