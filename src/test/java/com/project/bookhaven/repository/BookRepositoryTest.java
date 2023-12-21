package com.project.bookhaven.repository;

import com.project.bookhaven.model.Book;
import com.project.bookhaven.repository.book.BookRepository;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = {"classpath:database/add-books.sql",
        "classpath:database/add-categories.sql",
        "classpath:database/add-books-categories.sql" },
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {"classpath:database/delete-books-categories.sql",
        "classpath:database/delete-books.sql",
        "classpath:database/delete-categories-table.sql" },
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @Test
    @DisplayName("Find all books with not exists category")
    void findAllByCategoryId_NonValidCategory_ReturnsEmptyList() {
        List<Book> actual = bookRepository.findAllByCategory(PageRequest.of(0, 10), 100L);
        Assertions.assertEquals(0, actual.size());
    }

    @Test
    @DisplayName("Find all books with valid category")
    void findAllByCategoryId_ValidCategory_ReturnsExpectedBooks() {
        List<Book> actual = bookRepository.findAllByCategory(PageRequest.of(0, 10), 2L);
        Assertions.assertEquals(1, actual.size());
        Assertions.assertEquals("Test1", actual.get(0).getTitle());
    }
}
