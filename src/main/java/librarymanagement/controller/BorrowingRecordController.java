package librarymanagement.controller;
import javax.transaction.Transactional;
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

    // Create a new borrowing record
    @PostMapping("/borrow/{bookId}/patrons/{patronId}")
    public ResponseEntity<BorrowingRecord> borrowBook(
            @PathVariable Long bookId,
            @PathVariable Long patronId) {
        BorrowingRecord savedRecord = borrowingRecordService.borrowBook(bookId, patronId);
        return ResponseEntity.status(201).body(savedRecord);
    }
}