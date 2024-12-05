package librarymanagement.service;

import librarymanagement.model.BorrowingRecord;
import librarymanagement.repository.BookRepository;
import librarymanagement.repository.BorrowingRecordRepository;
import librarymanagement.repository.PatronRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import librarymanagement.model.Book;
import librarymanagement.model.Patron;
import librarymanagement.exception.ResourceNotFoundException;
import java.util.Date;

@Service
@Transactional
public class BorrowingRecordService {
    @Autowired
    private BookRepository bookRepo;

    @Autowired
    private PatronRepository patronRepo;

    @Autowired
    private BorrowingRecordRepository borrowingRecordRepo;

    // Create a new borrowing record
    @Transactional
    public BorrowingRecord borrowBook(Long bookId, Long patronId) {

        Book book = bookRepo.findById(bookId).orElseThrow(() ->
                new ResourceNotFoundException("Book", "ID", bookId));
        Patron patron = patronRepo.findById(patronId).orElseThrow(() ->
                new ResourceNotFoundException("Patron", "ID", patronId));

        BorrowingRecord borrowingRecord = new BorrowingRecord();
        Date currentDate = new Date();
        // Print the current date
        System.out.println("Current Date: " + currentDate);
        borrowingRecord.setBook(book);
        borrowingRecord.setPatron(patron);
        borrowingRecord.setBorrowDate(currentDate);
        borrowingRecord.setStatus("Borrowed");
        return borrowingRecordRepo.save(borrowingRecord);
    }
}
