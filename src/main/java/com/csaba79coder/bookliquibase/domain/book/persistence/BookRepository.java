package com.csaba79coder.bookliquibase.domain.book.persistence;

import com.csaba79coder.bookliquibase.domain.book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// @Repository("bookRepository")
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
