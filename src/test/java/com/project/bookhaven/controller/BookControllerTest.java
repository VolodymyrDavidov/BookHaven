package com.project.bookhaven.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.bookhaven.dto.book.BookDto;
import com.project.bookhaven.dto.book.CreateBookRequestDto;
import com.project.bookhaven.repository.book.BookRepository;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = {"classpath:database/add-categories.sql",
        "classpath:database/add-books.sql",
        "classpath:database/add-books-categories.sql" },
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {"classpath:database/delete-books-categories.sql",
        "classpath:database/delete-books.sql",
        "classpath:database/delete-categories.sql" },
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class BookControllerTest {
    public static final Long INVALID_ID = -1L;

    private static MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private BookRepository bookRepository;

    @BeforeAll
    static void beforeAll(@Autowired WebApplicationContext applicationContext) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    @DisplayName("Create book with valid dto")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void createBook_validRequestDto_returnsDto() throws Exception {
        CreateBookRequestDto requestDto = new CreateBookRequestDto(
                "test title", "test author", "978-0-06-112638-4",
                BigDecimal.valueOf(9.99), "test description", "test image", List.of(1L));

        MvcResult result = mockMvc.perform(post("/api/books")
                        .content(objectMapper.writeValueAsString(requestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        BookDto actual = objectMapper.readValue(result.getResponse()
                .getContentAsString(), BookDto.class);
        assertThat(actual)
                .hasFieldOrPropertyWithValue("id", 3L)
                .hasFieldOrPropertyWithValue("title", requestDto.title())
                .hasFieldOrPropertyWithValue("author", requestDto.author())
                .hasFieldOrPropertyWithValue("price", requestDto.price())
                .hasFieldOrPropertyWithValue("description", requestDto.description())
                .hasFieldOrPropertyWithValue("coverImage", requestDto.coverImage());
    }

    @Test
    @DisplayName("Get book by valid id")
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    void getBookById_validId_returnsDto() throws Exception {
        Long id = 1L;

        BookDto expected = new BookDto();
        expected.setTitle("Test2");
        expected.setPrice(BigDecimal.valueOf(11.55));

        MvcResult result = mockMvc.perform(get("/api/books/" + id))
                .andExpect(status().isOk())
                .andReturn();

        BookDto actual = objectMapper.readValue(result.getResponse()
                .getContentAsString(), BookDto.class);
        assertThat(actual).isNotNull()
                .hasFieldOrPropertyWithValue("title", expected.getTitle())
                .hasFieldOrPropertyWithValue("price", expected.getPrice())
                .hasFieldOrPropertyWithValue("id", id);
    }

    @Test
    @DisplayName("Delete book by invalid id")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void deleteBook_ByInvalidId() throws Exception {

        mockMvc.perform(delete("/api/books/" + INVALID_ID))
                .andExpect(status().isBadRequest())
                .andReturn();
    }
}
