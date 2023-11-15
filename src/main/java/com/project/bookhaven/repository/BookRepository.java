package com.project.bookhaven.repository;

import com.project.bookhaven.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
