package com.csaba79coder.bookliquibase.domain.book.persistence;

import com.csaba79coder.bookliquibase.domain.book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

// @Repository("bookRepository")
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findBookById(UUID id);
    void deleteBookById(UUID id);
}
