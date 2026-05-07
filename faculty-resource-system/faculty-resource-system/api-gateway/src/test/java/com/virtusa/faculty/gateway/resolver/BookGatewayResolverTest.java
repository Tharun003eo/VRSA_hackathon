package com.virtusa.faculty.gateway.resolver;

import com.virtusa.faculty.book.dto.BookInput;
import com.virtusa.faculty.book.entity.Book;
import com.virtusa.faculty.book.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookGatewayResolverTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookGatewayResolver resolver;

    @Test
    void testGetBookById() {
        Book book = Book.builder().id(1L).title("DSA").build();

        when(bookService.getBookById(1L)).thenReturn(book);

        Book result = resolver.getBookById(1L);

        assertEquals("DSA", result.getTitle());
    }

    @Test
    void testSearchBooks() {
        when(bookService.searchBooks("data"))
                .thenReturn(List.of(new Book(), new Book()));

        List<Book> result = resolver.searchBooks("data");

        assertEquals(2, result.size());
    }

    @Test
    void testCreateBook() {
        BookInput input = new BookInput();
        input.setIsbn("ISBN-1");
        input.setTitle("DSA");
        input.setAuthorId("1");
        input.setVersion("v1");

        Book saved = Book.builder().title("DSA").build();

        when(bookService.createBook(any())).thenReturn(saved);

        Book result = resolver.createBook(input);

        assertEquals("DSA", result.getTitle());
    }

    @Test
    void testUpdateBook() {
        BookInput input = new BookInput();
        input.setTitle("New");

        Book updated = Book.builder().title("New").build();

        when(bookService.updateBook(eq(1L), any())).thenReturn(updated);

        Book result = resolver.updateBook(1L, input);

        assertEquals("New", result.getTitle());
    }

    @Test
    void testDeleteBook() {
        Boolean result = resolver.deleteBook(1L);

        assertTrue(result);
        verify(bookService).deleteBook(1L);
    }

    @Test
    void testPublishBook() {
        Book book = Book.builder().state(null).build();

        when(bookService.publishBook(1L)).thenReturn(book);

        Book result = resolver.publishBook(1L);

        assertNotNull(result);
    }

    @Test
    void testVersionBook() {
        BookInput input = new BookInput();
        input.setTitle("v2");
        input.setVersion("v2");

        Book book = Book.builder().version("v2").build();

        when(bookService.versionBook(eq(1L), any())).thenReturn(book);

        Book result = resolver.versionBook(1L, input);

        assertEquals("v2", result.getVersion());
    }
}