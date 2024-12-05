package librarymanagement.service;

import librarymanagement.exception.ConflictException;
import librarymanagement.exception.ResourceNotFoundException;
import librarymanagement.model.Patron;
import librarymanagement.repository.PatronRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PatronService {

    @Autowired
    private PatronRepository patronRepo;

    public Patron createPatron(Patron patron) {
        if (patronRepo.findByName(patron.getName()).isPresent()) {
            throw new ConflictException("A patron with the name " + patron.getName() + " already exists.");
        }
        return patronRepo.save(patron);
    }

    public List<Patron> getAllPatrons() {
        return patronRepo.findAll();
    }

    public Patron getPatronById(long id) {
        return patronRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Patron", "id", id));
    }

    public void deletePatronById(long id) {
        Patron patron = patronRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Patron", "id", id));
        patronRepo.delete(patron);
    }

    public Patron updatePatronById(long id, Patron patron) {
        Patron patronToUpdate = patronRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Patron", "id", id));
        patronToUpdate.setName(patron.getName());
        patronToUpdate.setEmail(patron.getEmail());
        patronToUpdate.setPhone(patron.getPhone());
        return patronRepo.save(patronToUpdate);
    }
}
