package com.virtusa.faculty.gateway.resolver;

import com.virtusa.faculty.book.dto.BookInput;
import com.virtusa.faculty.book.entity.Book;
import com.virtusa.faculty.book.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookGatewayResolver {

    private final BookService bookService;

    // ----------- Queries -----------

    // getBookById
    @QueryMapping
    public Book getBookById(@Argument Long id) {
        return bookService.getBookById(id);
    }

    // searchBooks
    @QueryMapping
    public List<Book> searchBooks(@Argument String keyword) {
        return bookService.searchBooks(keyword);
    }

    // getBooksByFaculty
    @QueryMapping
    public List<Book> getBooksByFaculty(@Argument String authorId) {
        return bookService.getBooksByFaculty(authorId);
    }

    // getBookVersions
    @QueryMapping
    public List<Book> getBookVersions(@Argument String isbn) {
        return bookService.getBookVersions(isbn);
    }

    // ----------- Mutations -----------

    // createBook
    @MutationMapping
    public Book createBook(@Argument BookInput input) {
        Book book = Book.builder()
                .isbn(input.getIsbn())
                .title(input.getTitle())
                .authorId(input.getAuthorId())
                .version(input.getVersion())
                .description(input.getDescription())
                .build();

        return bookService.createBook(book);
    }

    // updateBook
    @MutationMapping
    public Book updateBook(@Argument Long id, @Argument BookInput input) {
        Book updated = Book.builder()
                .title(input.getTitle())
                .description(input.getDescription())
                .build();

        return bookService.updateBook(id, updated);
    }

    // deleteBook
    @MutationMapping
    public Boolean deleteBook(@Argument Long id) {
        bookService.deleteBook(id);
        return true;
    }

    // publishBook
    @MutationMapping
    public Book publishBook(@Argument Long id) {
        return bookService.publishBook(id);
    }

    // archiveBook
    @MutationMapping
    public Book archiveBook(@Argument Long id) {
        return bookService.archiveBook(id);
    }

    // versionBook
    @MutationMapping
    public Book versionBook(@Argument Long id, @Argument BookInput input) {
        Book version = Book.builder()
                .title(input.getTitle())
                .version(input.getVersion())
                .description(input.getDescription())
                .build();

        return bookService.versionBook(id, version);
    }
}