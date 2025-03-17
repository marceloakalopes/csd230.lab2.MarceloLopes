package csd230.lab2.controllers;

import csd230.lab2.entities.Magazine;
import csd230.lab2.respositories.MagazineRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/rest/magazine")
public class MagazineRestController {

    private final MagazineRepository magazineRepository;

    public MagazineRestController(MagazineRepository magazineRepository) {
        this.magazineRepository = magazineRepository;
    }

    // GET all magazines
    @GetMapping
    public List<Magazine> all() {
        return magazineRepository.findAll();
    }

    // GET magazine by id
    @GetMapping("/{id}")
    public Magazine getMagazine(@PathVariable Long id) {
        return magazineRepository.findById(id)
                .orElseThrow(() -> new MagazineNotFoundException(id));
    }

    // POST a new magazine
    @PostMapping
    public Magazine newMagazine(@RequestBody Magazine newMagazine) {
        return magazineRepository.save(newMagazine);
    }

    // PUT (update) a magazine
    @PutMapping("/{id}")
    public Magazine replaceMagazine(@RequestBody Magazine newMagazine, @PathVariable Long id) {
        return magazineRepository.findById(id)
                .map(magazine -> {
                    // Update fields as appropriate (example below)
                    magazine.setTitle(newMagazine.getTitle());
                    magazine.setDescription(newMagazine.getDescription());
                    // add other field updates if needed
                    return magazineRepository.save(magazine);
                })
                .orElseGet(() -> magazineRepository.save(newMagazine));
    }

    // DELETE a magazine by id
    @DeleteMapping("/{id}")
    public void deleteMagazine(@PathVariable Long id) {
        if (!magazineRepository.existsById(id)) {
            throw new MagazineNotFoundException(id);
        }
        magazineRepository.deleteById(id);
    }
}


class MagazineNotFoundException extends RuntimeException {
    public MagazineNotFoundException(Long id) {
        super("Could not find magazine " + id);
    }
}

@RestControllerAdvice
class MagazineNotFoundAdvice {

    @ExceptionHandler(MagazineNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String magazineNotFoundHandler(MagazineNotFoundException ex) {
        return ex.getMessage();
    }
}