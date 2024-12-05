package librarymanagement.controller;

import librarymanagement.service.BorrowingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import librarymanagement.model.BorrowingRecord;

@RestController
@RequestMapping("/api")
public class BorrowingRecordController {

    @Autowired
    private BorrowingRecordService borrowingRecordService;

    /**
     * Create a new borrowing record.
     *
     * @param bookId   the ID of the book to be borrowed
     * @param patronId the ID of the patron borrowing the book
     * @return a ResponseEntity containing the created BorrowingRecord and HTTP status 201 (Created)
     */
    @PostMapping("/borrow/{bookId}/patrons/{patronId}")
    public ResponseEntity<BorrowingRecord> borrowBook(
            @PathVariable Long bookId,
            @PathVariable Long patronId) {
        BorrowingRecord savedRecord = borrowingRecordService.borrowBook(bookId, patronId);
        return ResponseEntity.status(201).body(savedRecord);
    }

    /**
     * Update an existing borrowing record.
     *
     * @param bookId        the ID of the book being borrowed
     * @param patronId      the ID of the patron borrowing the book
     * @param updatedRecord the updated borrowing record details
     * @return a ResponseEntity containing the updated BorrowingRecord and HTTP status 200 (OK)
     */
    @PutMapping("/borrow/{bookid}/patrons/{patronId}")
    public ResponseEntity<BorrowingRecord> updateBorrowingRecord(
            @PathVariable Long bookId,
            @PathVariable Long patronId,
            @RequestBody BorrowingRecord updatedRecord) {
        BorrowingRecord updated = borrowingRecordService.updateBorrowingRecord(bookId, patronId, updatedRecord);
        return ResponseEntity.ok(updated);
    }
}