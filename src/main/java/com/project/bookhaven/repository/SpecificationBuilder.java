package com.project.bookhaven.repository;

import com.project.bookhaven.dto.BookSearchParameters;
import org.springframework.data.jpa.domain.Specification;

public interface SpecificationBuilder<T> {
    Specification<T> build(BookSearchParameters bookSearchParameters);
}
