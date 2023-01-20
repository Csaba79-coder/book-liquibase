package com.csaba79coder.bookliquibase;

import com.csaba79coder.bookliquibase.domain.book.entity.Book;
import com.csaba79coder.bookliquibase.domain.book.persistence.BookRepository;
import com.csaba79coder.bookliquibase.domain.book.service.BookService;
import com.csaba79coder.bookliquibase.domain.book.value.Genre;
import com.csaba79coder.bookliquibase.domain.controller.BookController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
@AutoConfigureMockMvc(addFilters = false)
public class BookControllerIT {

    // https://howtodoinjava.com/spring-boot2/testing/spring-boot-mockmvc-example/
    // https://spring.io/guides/gs/testing-web/
    // https://www.youtube.com/watch?v=tG-TwkIhjOk
    // endpoint testing & integration testing

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookService bookService;

    @MockBean
    private BookRepository bookRepository;

    @BeforeEach
    void init() {
        bookRepository.deleteAll();
    }

    @Test
    @DisplayName("testRenderAllBooksWithEmptyResult")
    void testRenderAllBooksWithEmptyResult() throws Exception {
        // Given
        System.out.println(bookRepository.findAll().size());
        
        // When
        
        // Then
    }

    @Test
    @DisplayName("testRenderAllBooksWithNonEmptyResult")
    void testRenderAllBooksWithNonEmptyResult() throws Exception {
        // Given
        Book testBook = createDummyBookForTest();

        // When
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testBook))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Then
        System.out.println(response.getClass().getName());
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
