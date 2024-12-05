package librarymanagement.service;

import librarymanagement.exception.ResourceNotFoundException;
import librarymanagement.model.BorrowingRecord;
import librarymanagement.repository.BorrowingRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BorrowingRecordServiceTest {

    @Mock
    private BorrowingRecordRepository borrowingRecordRepo;

    @InjectMocks
    private BorrowingRecordService borrowingRecordService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void updateBorrowingRecord_success() throws Exception {
        BorrowingRecord updatedRecord = new BorrowingRecord();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date returnDate = dateFormat.parse("2023-12-31");
        updatedRecord.setReturnDate(returnDate);
        updatedRecord.setStatus("Returned");

        BorrowingRecord existingRecord = new BorrowingRecord();
        when(borrowingRecordRepo.findById(1L)).thenReturn(Optional.of(existingRecord));
        when(borrowingRecordRepo.save(existingRecord)).thenReturn(existingRecord);

        BorrowingRecord result = borrowingRecordService.updateBorrowingRecord(1L, 1L, updatedRecord);

        assertEquals(existingRecord, result);
        assertEquals(returnDate, result.getReturnDate());
        assertEquals("Returned", result.getStatus());
        verify(borrowingRecordRepo, times(1)).save(existingRecord);
    }

    @Test
    void updateBorrowingRecord_notFound() {
        BorrowingRecord updatedRecord = new BorrowingRecord();
        when(borrowingRecordRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> borrowingRecordService.updateBorrowingRecord(1L, 1L, updatedRecord));
        verify(borrowingRecordRepo, never()).save(any(BorrowingRecord.class));
    }
}