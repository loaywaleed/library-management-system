package librarymanagement.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Title cannot be null")
    @Size(min = 1, max = 80, message = "Title must be between 1 and 80 characters")
    @Column
    private String title;

    @NotNull(message = "Author cannot be null")
    @Size(min = 1, max = 80, message = "Author must be between 1 and 80 characters")
    @Column
    private String author;

    @NotNull(message = "ISBN cannot be null")
    @Size(min = 10, max = 13, message = "ISBN must be between 10 and 13 characters")
    @Column
    private String isbn;

    @NotNull(message = "Publication Year cannot be null")
    @Column
    private int publicationYear;

    // Default constructor
    public Book() {
    }

    // Constructor with parameters
    public Book(String title, String author, String isbn, int publicationYear) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publicationYear = publicationYear;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }
}