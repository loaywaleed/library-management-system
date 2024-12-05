package librarymanagement.controller;

import librarymanagement.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import librarymanagement.service.BookService;
import java.util.List;

@RestController
@RequestMapping("/api")
public class BookController {

    @Autowired
    private BookService bookService;

    /**
     * Get all books.
     *
     * @return a list of all books
     */
    @GetMapping(value = "/books")
    public List<Book> getBook() {
        return bookService.getAllBooks();
    }

    /**
     * Create a new book.
     *
     * @param book the book to create
     * @return a ResponseEntity containing the created book and HTTP status 201 (Created)
     */
    @PostMapping(value = "/books")
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        Book savedBook = bookService.createBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
    }

    /**
     * Get a book by ID.
     *
     * @param id the ID of the book to retrieve
     * @return the book with the specified ID
     */
    @GetMapping(value = "/books/{id}")
    public Book getPatronById(@PathVariable long id) {
        return bookService.getBookById(id);
    }

    /**
     * Delete a book by ID.
     *
     * @param id the ID of the book to delete
     */
    @DeleteMapping(value = "/books/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBookById(@PathVariable long id) {
        bookService.deleteBookById(id);
    }

    /**
     * Update a book by ID.
     *
     * @param id the ID of the book to update
     * @param book the updated book details
     * @return the updated book
     */
    @PutMapping(value = "/books/{id}")
    public Book updateBookById(@PathVariable long id, @RequestBody Book book) {
        return bookService.updateBookById(id, book);
    }
}