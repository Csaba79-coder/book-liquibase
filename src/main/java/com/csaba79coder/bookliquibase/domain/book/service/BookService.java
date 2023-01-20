package com.csaba79coder.bookliquibase.domain.book.service;

import com.csaba79coder.bookliquibase.domain.book.entity.Book;
import com.csaba79coder.bookliquibase.domain.book.persistence.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {

    private final BookRepository bookRepository;

    public List<Book> renderAllBooks() {
        return bookRepository.findAll();
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }
}
