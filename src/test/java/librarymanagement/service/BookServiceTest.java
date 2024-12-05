package librarymanagement.service;


import librarymanagement.service.BookService;
import librarymanagement.exception.ConflictException;
import librarymanagement.exception.ResourceNotFoundException;
import librarymanagement.model.Book;
import librarymanagement.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.List;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createBook_success() {
        Book book = new Book();
        book.setIsbn("1234567890");
        when(bookRepository.findByIsbn(book.getIsbn())).thenReturn(Optional.empty());
        when(bookRepository.save(book)).thenReturn(book);

        Book createdBook = bookService.createBook(book);

        assertEquals(book, createdBook);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void createBook_conflict() {
        Book book = new Book();
        book.setIsbn("1234567890");
        when(bookRepository.findByIsbn(book.getIsbn())).thenReturn(Optional.of(book));

        assertThrows(ConflictException.class, () -> bookService.createBook(book));
        verify(bookRepository, never()).save(book);
    }

    @Test
    void getAllBooks_success() {
        List<Book> books = Arrays.asList(new Book(), new Book());
        when(bookRepository.findAll()).thenReturn(books);

        List<Book> result = bookService.getAllBooks();

        assertEquals(books, result);
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void getBookById_success() {
        Book book = new Book();
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Book result = bookService.getBookById(1L);

        assertEquals(book, result);
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void getBookById_notFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> bookService.getBookById(1L));
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void deleteBookById_success() {
        Book book = new Book();
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        bookService.deleteBookById(1L);

        verify(bookRepository, times(1)).delete(book);
    }

    @Test
    void deleteBookById_notFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> bookService.deleteBookById(1L));
        verify(bookRepository, never()).delete(any(Book.class));
    }

    @Test
    void updateBookById_success() {
        Book book = new Book();
        book.setTitle("New Title");
        book.setAuthor("New Author");
        book.setIsbn("1234567890");
        book.setPublicationYear(2023);

        Book existingBook = new Book();
        when(bookRepository.findById(1L)).thenReturn(Optional.of(existingBook));
        when(bookRepository.save(existingBook)).thenReturn(existingBook);

        Book updatedBook = bookService.updateBookById(1L, book);

        assertEquals(existingBook, updatedBook);
        assertEquals("New Title", updatedBook.getTitle());
        assertEquals("New Author", updatedBook.getAuthor());
        assertEquals("1234567890", updatedBook.getIsbn());
        assertEquals(2023, updatedBook.getPublicationYear());
        verify(bookRepository, times(1)).save(existingBook);
    }

    @Test
    void updateBookById_notFound() {
        Book book = new Book();
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> bookService.updateBookById(1L, book));
        verify(bookRepository, never()).save(any(Book.class));
    }
}