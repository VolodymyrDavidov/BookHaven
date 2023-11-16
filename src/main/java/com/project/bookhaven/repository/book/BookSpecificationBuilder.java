package com.project.bookhaven.repository.book;

import com.project.bookhaven.dto.BookSearchParameters;
import com.project.bookhaven.model.Book;
import com.project.bookhaven.repository.SpecificationBuilder;
import com.project.bookhaven.repository.SpecificationProviderManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BookSpecificationBuilder implements SpecificationBuilder<Book> {
    private final SpecificationProviderManager<Book> bookSpecificationProviderManager;

    @Override
    public Specification<Book> build(BookSearchParameters bookSearchParameters) {
        Specification<Book> specification = Specification.where(null);
        if (bookSearchParameters.titles() != null && bookSearchParameters.titles().length > 0) {
            specification = specification.and(bookSpecificationProviderManager
                    .getSpecificationProvider("title")
                    .getSpecification(bookSearchParameters.titles()));
        }
        if (bookSearchParameters.authors() != null && bookSearchParameters.authors().length > 0) {
            specification = specification.and(bookSpecificationProviderManager
                    .getSpecificationProvider("author")
                    .getSpecification(bookSearchParameters.authors()));
        }
        return specification;
    }
}
