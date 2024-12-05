package librarymanagement.service;

import librarymanagement.exception.ConflictException;
import librarymanagement.exception.ResourceNotFoundException;
import librarymanagement.model.Patron;
import librarymanagement.repository.PatronRepository;
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

class PatronServiceTest {

    @Mock
    private PatronRepository patronRepo;

    @InjectMocks
    private PatronService patronService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createPatron_success() {
        Patron patron = new Patron();
        patron.setName("John Doe");
        when(patronRepo.findByName(patron.getName())).thenReturn(Optional.empty());
        when(patronRepo.save(patron)).thenReturn(patron);

        Patron createdPatron = patronService.createPatron(patron);

        assertEquals(patron, createdPatron);
        verify(patronRepo, times(1)).save(patron);
    }

    @Test
    void createPatron_conflict() {
        Patron patron = new Patron();
        patron.setName("John Doe");
        when(patronRepo.findByName(patron.getName())).thenReturn(Optional.of(patron));

        assertThrows(ConflictException.class, () -> patronService.createPatron(patron));
        verify(patronRepo, never()).save(patron);
    }

    @Test
    void getAllPatrons_success() {
        List<Patron> patrons = Arrays.asList(new Patron(), new Patron());
        when(patronRepo.findAll()).thenReturn(patrons);

        List<Patron> result = patronService.getAllPatrons();

        assertEquals(patrons, result);
        verify(patronRepo, times(1)).findAll();
    }

    @Test
    void getPatronById_success() {
        Patron patron = new Patron();
        when(patronRepo.findById(1L)).thenReturn(Optional.of(patron));

        Patron result = patronService.getPatronById(1L);

        assertEquals(patron, result);
        verify(patronRepo, times(1)).findById(1L);
    }

    @Test
    void getPatronById_notFound() {
        when(patronRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> patronService.getPatronById(1L));
        verify(patronRepo, times(1)).findById(1L);
    }

    @Test
    void deletePatronById_success() {
        Patron patron = new Patron();
        when(patronRepo.findById(1L)).thenReturn(Optional.of(patron));

        patronService.deletePatronById(1L);

        verify(patronRepo, times(1)).delete(patron);
    }

    @Test
    void deletePatronById_notFound() {
        when(patronRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> patronService.deletePatronById(1L));
        verify(patronRepo, never()).delete(any(Patron.class));
    }

    @Test
    void updatePatronById_success() {
        Patron patron = new Patron();
        patron.setName("Jane Doe");
        patron.setEmail("jane.doe@example.com");
        patron.setPhone("1234567890");

        Patron existingPatron = new Patron();
        when(patronRepo.findById(1L)).thenReturn(Optional.of(existingPatron));
        when(patronRepo.save(existingPatron)).thenReturn(existingPatron);

        Patron updatedPatron = patronService.updatePatronById(1L, patron);

        assertEquals(existingPatron, updatedPatron);
        assertEquals("Jane Doe", updatedPatron.getName());
        assertEquals("jane.doe@example.com", updatedPatron.getEmail());
        assertEquals("1234567890", updatedPatron.getPhone());
        verify(patronRepo, times(1)).save(existingPatron);
    }

    @Test
    void updatePatronById_notFound() {
        Patron patron = new Patron();
        when(patronRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> patronService.updatePatronById(1L, patron));
        verify(patronRepo, never()).save(any(Patron.class));
    }
}