package com.csaba79coder.bookliquibase;

import com.csaba79coder.bookliquibase.domain.book.entity.Book;
import com.csaba79coder.bookliquibase.domain.book.persistence.BookRepository;
import com.csaba79coder.bookliquibase.domain.book.service.BookService;
import com.csaba79coder.bookliquibase.domain.book.value.Availability;
import com.csaba79coder.bookliquibase.domain.book.value.Genre;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.observation.Observation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

import static org.assertj.core.api.BDDAssertions.then;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
public class BookControllerIT extends BookLiquibaseApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    void init() {

        bookRepository.deleteAll();
    }

    @Test
    @DisplayName("testRenderAllBooksWithEmptyResult")
    void testRenderAllBooksWithEmptyResult() throws Exception {
        // Given
        
        // When
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        
        // Then
        Observation.CheckedCallable<?, Throwable> throwableCheckedCallable = () -> then(bookRepository.findAll().size())
                .usingRecursiveComparison()
                .isEqualTo(0);
    }

    @Test
    @DisplayName("testRenderAllBooksWithNonEmptyResult")
    void testRenderAllBooksWithNonEmptyResult() throws Exception {
        // Given
        Book testBook = createAndSaveDummyBookForTest();
        bookRepository.save(testBook);

        // When
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testBook))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Then
        Observation.CheckedCallable<?, Throwable> throwableCheckedCallable = () -> then(bookRepository.findAll().size())
                .usingRecursiveComparison()
                .isEqualTo(1);
    }

    @Test
    @DisplayName("testRenderAllBooksWithDeletedAvailabilityInDB")
    void testRenderAllBooksWithDeletedAvailabilityInDb() throws Exception {
        // Given
        Book testBook = createAndSaveDummyBookForTest();
        bookService.saveBook(testBook);
        testBook.setAvailability(Availability.DELETED);
        bookService.saveBook(testBook);

        // When
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testBook))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Then
        then(bookRepository.findAll().size())
                .usingRecursiveComparison()
                .isEqualTo(0);
    }

    @Test
    @Disabled
    @DisplayName("addNewBook")
    void addNewBook() throws Exception {
        // Given
        Book testBook = new Book();
        testBook.setId(UUID.fromString("570c5ac2-1f1a-46c4-9215-ccd17e9858f4"));
        testBook.setTitle("Cat Among the Pigeons");
        testBook.setGenre(Genre.THRILLER);
        testBook.setIsbn(9780425175477L);

        // When
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testBook))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        // Then
        then(response)
                .usingRecursiveComparison()
                .isEqualTo(testBook);
    }

    @Test
    @DisplayName("deleteExistingBookById")
    void deleteExistingBookById() throws Exception {
        // Given
        Book testBook = createAndSaveDummyBookForTest();
        Book deletedBook = new Book();
        deletedBook.setAvailability(Availability.DELETED);

        // When
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.delete("/books/{bookId}", testBook.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testBook))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(204));

        // Then
        then(response)
               .usingRecursiveComparison()
               .isEqualTo(deletedBook);
        then(bookRepository.findAll().size())
                .usingRecursiveComparison()
                .isEqualTo(0);
    }

    @Test
    @DisplayName("deleteNonExistingIdExpectingErrorCode")
    void deleteNonExistingIdExpectingErrorCode() throws Exception {
        // Given
        UUID nonExistingId = UUID.fromString("4bc34bbf-6278-4586-9e62-429bc41edcf5");

        // When
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.delete("/books/{bookId}", nonExistingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        var body = response.andReturn().getResponse().getContentAsString();

        // Then
        then(body)
                // .usingRecursiveComparison()
                .isEqualTo("{ERROR_CODE_001=Book with id: %s was not found}");
    }

    private Book createAndSaveDummyBookForTest() {
        Book dummyBook = new Book();
        dummyBook.setId(UUID.fromString("570c5ac2-1f1a-46c4-9215-ccd17e9858f4"));
        dummyBook.setTitle("Cat Among the Pigeons");
        dummyBook.setGenre(Genre.THRILLER);
        dummyBook.setIsbn(9780425175477L);
        return bookRepository.save(dummyBook);
    }

    private Book createAndSaveDummyDeletedBookForTest() {
        Book dummyBook = new Book();
        dummyBook.setId(UUID.fromString("42a01fb4-520c-45f2-b8c2-46337130adbb"));
        dummyBook.setTitle("The Lord of the Rings");
        dummyBook.setGenre(Genre.THRILLER);
        dummyBook.setAvailability(Availability.DELETED);
        dummyBook.setIsbn(9788845292613L);
        return bookRepository.save(dummyBook);
    }
}
