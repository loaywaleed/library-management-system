package librarymanagement.service;

import librarymanagement.exception.ConflictException;
import librarymanagement.exception.ResourceNotFoundException;
import librarymanagement.model.Book;
import librarymanagement.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class BookService {

    @Autowired
    private BookRepository bookRepo;

    /**
     * Create a new book.
     *
     * @param book the book to create
     * @return the created book
     * @throws ConflictException if a book with the same ISBN already exists
     */
    @CachePut(value = "books", key = "#book.isbn")
    public Book createBook(Book book) {
        if (bookRepo.findByIsbn(book.getIsbn()).isPresent()) {
            throw new ConflictException("A book with the Isbn " + book.getIsbn() + " already exists.");
        }
        return bookRepo.save(book);
    }

    /**
     * Get all books.
     *
     * @return a list of all books
     */
    @Cacheable(value = "books")
    public List<Book> getAllBooks() {
        return bookRepo.findAll();
    }

    /**
     * Get a book by ID.
     *
     * @param id the ID of the book to retrieve
     * @return the book with the specified ID
     * @throws ResourceNotFoundException if the book is not found
     */
    @Cacheable(value = "books", key = "#id")
    public Book getBookById(long id) {
        return bookRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book", "id", id));
    }

    /**
     * Delete a book by ID.
     *
     * @param id the ID of the book to delete
     * @throws ResourceNotFoundException if the book is not found
     */
    @CacheEvict(value = "books", key = "#id")
    public void deleteBookById(long id) {
        Book book = bookRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book", "id", id));
        bookRepo.delete(book);
    }

    /**
     * Update a book by ID.
     *
     * @param id   the ID of the book to update
     * @param book the updated book details
     * @return the updated book
     * @throws ResourceNotFoundException if the book is not found
     */
    @CachePut(value = "books", key = "#id")
    public Book updateBookById(long id, Book book) {
        Book bookToUpdate = bookRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "id", id));
        bookToUpdate.setTitle(book.getTitle());
        bookToUpdate.setAuthor(book.getAuthor());
        bookToUpdate.setIsbn(book.getIsbn());
        bookToUpdate.setPublicationYear(book.getPublicationYear());
        return bookRepo.save(bookToUpdate);
    }
}