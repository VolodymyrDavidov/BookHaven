package com.project.bookhaven.repository.category;

import com.project.bookhaven.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
