package com.csaba79coder.bookliquibase;

import com.csaba79coder.bookliquibase.domain.book.entity.Book;
import com.csaba79coder.bookliquibase.domain.book.persistence.BookRepository;
import com.csaba79coder.bookliquibase.domain.book.service.BookService;
import com.csaba79coder.bookliquibase.domain.book.value.Genre;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.observation.Observation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.assertj.core.api.BDDAssertions.then;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
public class BookControllerIT extends BookLiquibaseApplicationTests {

    // https://howtodoinjava.com/spring-boot2/testing/spring-boot-mockmvc-example/
    // https://spring.io/guides/gs/testing-web/
    // https://www.youtube.com/watch?v=tG-TwkIhjOk
    // endpoint testing & integration testing

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
        Book testBook = createDummyBookForTest();
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

    private Book createDummyBookForTest() {
        Book dummyBook = new Book();
        dummyBook.setId(UUID.fromString("570c5ac2-1f1a-46c4-9215-ccd17e9858f4"));
        dummyBook.setTitle("Cat Among the Pigeons");
        dummyBook.setGenre(Genre.THRILLER);
        dummyBook.setIsbn(9781234567897L);
        return dummyBook;
    }
}
