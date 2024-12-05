package librarymanagement.controller;
//import com.library.rest.exceptions.ConflictException;
//import com.library.rest.exceptions.ResourceNotFoundException;
//import com.library.rest.models.Patron;
//import com.library.rest.repo.PatronRepo;
import librarymanagement.exception.ResourceNotFoundException;
import librarymanagement.exception.ConflictException;
import librarymanagement.model.Patron;
import librarymanagement.repository.PatronRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class PatronController {

    @Autowired
    private PatronRepository patronRepo;


    @GetMapping(value = "/patrons")
    public List<Patron> getPatron() {
        return patronRepo.findAll();
    }

    // Create a new patron
    @PostMapping(value = "/patrons")
    public ResponseEntity<Patron> createPatron(@RequestBody Patron patron) {
        if (patronRepo.findByName(patron.getName()).isPresent()) {
            throw new ConflictException("A patron with the name " + patron.getName() + " already exists.");
        }
        Patron savedPatron = patronRepo.save(patron);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPatron);
    }


    @GetMapping(value ="/patrons/{id}")
    public Patron getPatronById(@PathVariable long id) {
        return patronRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "id", id));
    }

    @DeleteMapping(value ="/patrons/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePatronById(@PathVariable long id) {
        Patron book = patronRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "id", id));
        patronRepo.delete(book);
    }

    @PutMapping(value = "/patrons/{id}")
    public Patron updateBookById(@PathVariable long id, @RequestBody Patron patron) {
        Patron patronToUpdate = patronRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "id", id));
        patronToUpdate.setName(patron.getName());
        patronToUpdate.setEmail(patron.getEmail());
        patronToUpdate.setPhone(patron.getPhone());
        return patronRepo.save(patronToUpdate);
    }
}