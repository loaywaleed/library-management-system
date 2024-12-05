package librarymanagement.controller;

import librarymanagement.model.Patron;
import librarymanagement.service.PatronService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;

@RestController
@RequestMapping("/api")
@Api(tags = "patrons")
public class PatronController {

    @Autowired
    private PatronService patronService;

    /**
     * Get all patrons.
     *
     * @return a list of all patrons
     */
    @ApiOperation(value = "Get all patrons")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Found the patrons")
    })
    @GetMapping(value = "/patrons")
    public List<Patron> getPatron() {
        return patronService.getAllPatrons();
    }

    /**
     * Create a new patron.
     *
     * @param patron the patron to create
     * @return a ResponseEntity containing the created patron and HTTP status 201 (Created)
     */
    @ApiOperation(value = "Create a new patron")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Patron created"),
            @ApiResponse(code = 409, message = "Conflict")
    })
    @PostMapping(value = "/patrons")
    public ResponseEntity<Patron> createPatron(@ApiParam("Patron to create") @RequestBody Patron patron) {
        Patron savedPatron = patronService.createPatron(patron);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPatron);
    }

    /**
     * Get a patron by ID.
     *
     * @param id the ID of the patron to retrieve
     * @return the patron with the specified ID
     */
    @ApiOperation(value = "Get a patron by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Found the patron"),
            @ApiResponse(code = 404, message = "Patron not found")
    })
    @GetMapping(value = "/patrons/{id}")
    public Patron getPatronById(@ApiParam("ID of the patron to retrieve") @PathVariable long id) {
        return patronService.getPatronById(id);
    }

    /**
     * Delete a patron by ID.
     *
     * @param id the ID of the patron to delete
     */
    @ApiOperation(value = "Delete a patron by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Patron deleted"),
            @ApiResponse(code = 404, message = "Patron not found")
    })
    @DeleteMapping(value = "/patrons/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePatronById(@ApiParam("ID of the patron to delete") @PathVariable long id) {
        patronService.deletePatronById(id);
    }

    /**
     * Update a patron by ID.
     *
     * @param id the ID of the patron to update
     * @param patron the updated patron details
     * @return the updated patron
     */
    @ApiOperation(value = "Update a patron by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Patron updated"),
            @ApiResponse(code = 404, message = "Patron not found")
    })
    @PutMapping(value = "/patrons/{id}")
    public Patron updatePatronById(@ApiParam("ID of the patron to update") @PathVariable long id, @ApiParam("Updated patron details") @RequestBody Patron patron) {
        return patronService.updatePatronById(id, patron);
    }
}