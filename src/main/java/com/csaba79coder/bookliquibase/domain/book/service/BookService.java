package com.csaba79coder.bookliquibase.domain.book.service;

import com.csaba79coder.bookliquibase.domain.book.entity.Book;
import com.csaba79coder.bookliquibase.domain.book.persistence.BookRepository;
import com.csaba79coder.bookliquibase.domain.book.value.Availability;
import com.csaba79coder.bookliquibase.domain.util.ISBN13Validator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static java.text.MessageFormat.format;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {

    private final BookRepository bookRepository;

    public List<Book> renderAllBooks() {
        return bookRepository.findAll();
    }

    public Book saveBook(Book book) {
        if (!ISBN13Validator.validISBN(book.getIsbn())) {
            String message = format("Book id: %s. Invalid ISBN number: %s", String.valueOf(book.getId()), String.valueOf(book.getIsbn()));
            log.info(message);
            throw  new InputMismatchException(message);
        } else {
            return bookRepository.save(book);
        }

    }

    @Transactional
    public void deleteBookById(UUID id) {
        Book book = bookRepository.findBookById(id)
                        .orElseThrow(() -> {
                            String message = format("Book with id: %s was not found", String.valueOf(id));
                            log.info(message);
                            return new NoSuchElementException(message);
                        });
        bookRepository.delete(book);
    }
}
