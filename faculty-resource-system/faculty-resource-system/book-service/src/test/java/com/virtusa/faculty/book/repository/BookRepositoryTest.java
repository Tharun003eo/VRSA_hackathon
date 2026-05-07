package com.virtusa.faculty.book.repository;

import com.virtusa.faculty.book.entity.Book;
import com.virtusa.faculty.book.entity.BookState;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    private Book createBook(String isbn, String title, String authorId, BookState state) {
        return Book.builder()
                .isbn(isbn)
                .title(title)
                .authorId(authorId)
                .version("v1") // REQUIRED field
                .state(state)
                .build();
    }

    @Test
    @DisplayName("Find by ISBN")
    void testFindByIsbn() {
        Book book = createBook("ISBN-1", "DSA", "1", BookState.DRAFT);
        bookRepository.save(book);

        Optional<Book> found = bookRepository.findByIsbn("ISBN-1");

        assertTrue(found.isPresent());
        assertEquals("DSA", found.get().getTitle());
    }

    @Test
    @DisplayName("Find by AuthorId")
    void testFindByAuthorId() {
        bookRepository.save(createBook("1", "A", "10", BookState.DRAFT));
        bookRepository.save(createBook("2", "B", "10", BookState.DRAFT));

        List<Book> books = bookRepository.findByAuthorId("10");

        assertEquals(2, books.size());
    }

    @Test
    @DisplayName("Find by State")
    void testFindByState() {
        bookRepository.save(createBook("1", "A", "1", BookState.PUBLISHED));

        List<Book> books = bookRepository.findByState(BookState.PUBLISHED);

        assertEquals(1, books.size());
    }

    @Test
    @DisplayName("Search by Title (case insensitive)")
    void testSearchByTitle() {
        bookRepository.save(createBook("1", "Data Structures", "1", BookState.DRAFT));

        List<Book> result = bookRepository.searchByTitle("data");

        assertFalse(result.isEmpty());
        assertEquals("Data Structures", result.get(0).getTitle());
    }
}