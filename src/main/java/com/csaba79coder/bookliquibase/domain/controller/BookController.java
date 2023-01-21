package com.csaba79coder.bookliquibase.domain.controller;

import com.csaba79coder.bookliquibase.domain.book.entity.Book;
import com.csaba79coder.bookliquibase.domain.book.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(value = "http://localhost:8080")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @RequestMapping(
            value = "/books",
            method = RequestMethod.GET,
            headers = "Accept=application/json")
    public ResponseEntity<List<Book>> renderAllBooks() {
        return ResponseEntity.status(200).body(bookService.renderAllBooks());
    }

    @RequestMapping(
            value = "/books",
            method = RequestMethod.POST,
            headers = "Accept=application/json")
    public ResponseEntity<Book> addNewBook(Book book) {
        return ResponseEntity.status(201).body(bookService.saveBook(book));
    }
}
