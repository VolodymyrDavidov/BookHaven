package com.project.bookhaven.repository.book;

import com.project.bookhaven.model.Book;
import com.project.bookhaven.repository.SpecificationProvider;
import com.project.bookhaven.repository.SpecificationProviderManager;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BookSpecificationProviderManager implements SpecificationProviderManager<Book> {
    private final List<SpecificationProvider<Book>> bookSpecificationProviderManager;

    @Override
    public SpecificationProvider<Book> getSpecificationProvider(String key) {
        return bookSpecificationProviderManager.stream()
                .filter(p -> p.getKey().equals(key))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Can't find correct specification"
                        + " provider for key: " + key));
    }
}
