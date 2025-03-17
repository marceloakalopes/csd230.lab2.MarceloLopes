package csd230.lab2.controllers;

import csd230.lab2.entities.DiscMag;
import csd230.lab2.respositories.DiscMagRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/rest/discmag")
public class DiscMagRestController {

    private final DiscMagRepository discMagRepository;

    public DiscMagRestController(DiscMagRepository discMagRepository) {
        this.discMagRepository = discMagRepository;
    }

    // GET all disc magazines
    @GetMapping
    public List<DiscMag> all() {
        return discMagRepository.findAll();
    }

    // GET disc magazine by id
    @GetMapping("/{id}")
    public DiscMag getDiscMag(@PathVariable Long id) {
        return discMagRepository.findById(id)
                .orElseThrow(() -> new DiscMagNotFoundException(id));
    }

    // POST a new disc magazine
    @PostMapping
    public DiscMag newDiscMag(@RequestBody DiscMag newDiscMag) {
        return discMagRepository.save(newDiscMag);
    }

    // PUT (update) a disc magazine
    @PutMapping("/{id}")
    public DiscMag replaceDiscMag(@RequestBody DiscMag newDiscMag, @PathVariable Long id) {
        return discMagRepository.findById(id)
                .map(discMag -> {
                    // Update fields as needed; adjust these as per your DiscMag properties
                    discMag.setTitle(newDiscMag.getTitle());
                    discMag.setDescription(newDiscMag.getDescription());
                    // add other field updates if necessary
                    return discMagRepository.save(discMag);
                })
                .orElseGet(() -> discMagRepository.save(newDiscMag));
    }

    // DELETE a disc magazine by id
    @DeleteMapping("/{id}")
    public void deleteDiscMag(@PathVariable Long id) {
        if (!discMagRepository.existsById(id)) {
            throw new DiscMagNotFoundException(id);
        }
        discMagRepository.deleteById(id);
    }
}

class DiscMagNotFoundException extends RuntimeException {
    public DiscMagNotFoundException(Long id) {
        super("Could not find disc magazine " + id);
    }
}

@RestControllerAdvice
class DiscMagNotFoundAdvice {

    @ExceptionHandler(DiscMagNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String discMagNotFoundHandler(DiscMagNotFoundException ex) {
        return ex.getMessage();
    }
}