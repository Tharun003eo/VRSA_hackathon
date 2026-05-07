package com.virtusa.faculty.book.repository;

import com.virtusa.faculty.book.entity.Book;
import com.virtusa.faculty.book.entity.BookState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByIsbn(String isbn);

    List<Book> findByAuthorId(String authorId);

    List<Book> findByState(BookState state);


    @Query("SELECT b FROM Book b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Book> searchByTitle(@Param("keyword") String keyword);
}