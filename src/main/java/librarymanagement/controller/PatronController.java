package librarymanagement.controller;
import librarymanagement.model.Patron;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import librarymanagement.service.PatronService;
import java.util.List;


@RestController
@RequestMapping("/api")
public class PatronController {

    @Autowired
    private PatronService patronService;


    @GetMapping(value = "/patrons")
    public List<Patron> getPatron() {
        return patronService.getAllPatrons();
    }

    // Create a new patron
    @PostMapping(value = "/patrons")
    public ResponseEntity<Patron> createPatron(@RequestBody Patron patron) {
        Patron savedPatron = patronService.createPatron(patron);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPatron);
    }

    @GetMapping(value ="/patrons/{id}")
    public Patron getPatronById(@PathVariable long id) {
        return patronService.getPatronById(id);
    }

    @DeleteMapping(value ="/patrons/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePatronById(@PathVariable long id) {
        patronService.deletePatronById(id);
    }

    @PutMapping(value = "/patrons/{id}")
    public Patron updateBookById(@PathVariable long id, @RequestBody Patron patron) {
        return patronService.updatePatronById(id, patron);
    }
}