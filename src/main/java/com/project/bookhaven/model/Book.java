package com.project.bookhaven.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Data

@SQLDelete(sql = "UPDATE books SET is_deleted = TRUE WHERE id=?")
@Where(clause = "is_deleted = FALSE")
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Title cannot be blank")
    private String title;
    @NotBlank(message = "Author cannot be blank")
    private String author;
    @NotNull
    @Pattern(regexp = "^(978|979)-\\d{1,5}-\\d{2,7}-\\d{1,6}-[\\dX]$")
    private String isbn;
    @Positive(message = "Price must be positive")
    @DecimalMin(value = "0.01", inclusive = true, message = "Price must be greater than 0")
    private BigDecimal price;
    @NotBlank(message = "Description cannot be blank")
    private String description;
    @NotBlank(message = "CoverImage cannot be blank")
    private String coverImage;
    @Column(nullable = false)
    private boolean isDeleted = false;

}
