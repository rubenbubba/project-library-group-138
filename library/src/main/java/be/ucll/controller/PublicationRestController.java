package be.ucll.controller;

import be.ucll.model.Publication;
import be.ucll.service.PublicationService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/publications")
public class PublicationRestController {

    private final PublicationService service;

    public PublicationRestController(PublicationService service) {
        this.service = service;
    }

    /* ----------- GET ----------- */

    @GetMapping
    public List<Publication> get(@RequestParam(required = false) String title,
                                 @RequestParam(required = false) String type) {
        return service.getPublications(title, type);
    }

    @GetMapping("/stock/{min}")
    public List<Publication> stock(@PathVariable int min) {
        return service.getPublicationsWithCopies(min);
    }

    /* ----------- POST ----------- */
    @PostMapping
    public ResponseEntity<Publication> add(@Valid @RequestBody Publication p) {
        return new ResponseEntity<>(service.add(p), HttpStatus.OK);
    }
}
