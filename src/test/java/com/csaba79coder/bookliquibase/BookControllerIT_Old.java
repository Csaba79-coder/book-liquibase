package com.csaba79coder.bookliquibase;

import com.csaba79coder.bookliquibase.domain.book.entity.Book;
import com.csaba79coder.bookliquibase.domain.book.persistence.BookRepository;
import com.csaba79coder.bookliquibase.domain.book.service.BookService;
import com.csaba79coder.bookliquibase.domain.book.value.Genre;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.assertj.core.api.BDDAssertions.then;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// @ExtendWith(SpringExtension.class)
// @SpringBootTest
// @WebMvcTest(BookController.class)
@Disabled
@AutoConfigureMockMvc(addFilters = false)
public class BookControllerIT_Old {

    /*

    // @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ObjectMapper objectMapper;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private BookService bookService;

    @BeforeEach
    void init() {
        bookRepository.deleteAll();
    }

    @Test
    @DisplayName("renderAllBooksEmptyResult")
    void renderAllBooksEmptyResult() throws Exception {
        // https://spring.io/blog/2014/12/02/latest-jackson-integration-improvements-in-spring
        // https://assertj.github.io/doc/
        // https://www.baeldung.com/spring-mvc-set-json-content-type
        // https://stackoverflow.com/questions/62022071/how-to-check-values-in-response-body-with-mockmvc-assertionerror-status-expec
        
        // Given

        // When
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .get("/books")
                .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Then
        then(response)
                .isNotNull();
    }

    @Test
    @DisplayName("renderAllBooksWithNonEmptyResult")
    void renderAllBooksWithNonEmptyResult() throws Exception {
        // https://stackoverflow.com/questions/18336277/how-to-check-string-in-response-body-with-mockmvc

        // Given
        Book testBook = createDummyBookForTest();
        bookService.saveBook(testBook);

        System.out.println(bookRepository.findAll().size());

        // When
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                        .get("/books")
                        // .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testBook))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // String content = result.getResponse().getContentAsString();

        // Then
        then(response.toString())
                .isEqualTo(testBook);
    }

    private Book createDummyBookForTest() {
        Book dummyBook = new Book();
        dummyBook.setId(UUID.fromString("570c5ac2-1f1a-46c4-9215-ccd17e9858f4"));
        dummyBook.setTitle("Cat Among the Pigeons");
        dummyBook.setGenre(Genre.THRILLER);
        dummyBook.setIsbn(9781234567897L);
        return dummyBook;
    }


     */
}
