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


    @GetMapping(value = "/books")
    public List<Book> getBook() {
        return bookService.getAllBooks();
    }

    // Create a new book
    @PostMapping(value = "/books")
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        Book savedBook = bookService.createBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
    }

    @GetMapping(value ="/books/{id}")
    public Book getPatronById(@PathVariable long id) {
        return bookService.getBookById(id);
    }

    @DeleteMapping(value ="/books/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBookById(@PathVariable long id) {
        bookService.deleteBookById(id);
    }

    @PutMapping(value = "/books/{id}")
    public Book updateBookById(@PathVariable long id, @RequestBody Book book) {
        return bookService.updateBookById(id, book);
    }
}