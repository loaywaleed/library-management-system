package com.example.librarymanagement.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDateTime;

// Enum for Book Status
enum BookStatus {
    AVAILABLE,
    BORROWED,
    RESERVED
}

@Data
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Author is required")
    private String author;

    @Min(value = 1000, message = "Publication year must be after 1000")
    @Max(value = 2024, message = "Publication year cannot be in the future")
    private Integer publicationYear;

    @Column(unique = true)
    @Pattern(regexp = "\\d{10}|\\d{13}", message = "ISBN must be 10 or 13 digits")
    private String isbn;

    @Enumerated(EnumType.STRING)
    private BookStatus status = BookStatus.AVAILABLE;
}