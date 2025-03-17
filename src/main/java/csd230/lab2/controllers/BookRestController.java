package csd230.lab2.controllers;

import csd230.lab2.entities.Book;
import csd230.lab2.respositories.BookRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/rest/book")
public class BookRestController {

    private final BookRepository bookRepository;

    public BookRestController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // GET all books
    @GetMapping
    public List<Book> all() {
        return bookRepository.findAll();
    }

    // GET book by id
    @GetMapping("/{id}")
    public Book getBook(@PathVariable Long id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            return book.get();
        } else {
            throw new BookNotFoundException(id);
        }
    }

    // POST a new book
    @PostMapping
    public Book newBook(@RequestBody Book newBook) {
        return bookRepository.save(newBook);
    }

    // PUT (update) an existing book or create if not found
    @PutMapping("/{id}")
    public Book replaceBook(@RequestBody Book newBook, @PathVariable Long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {

            Book book = optionalBook.get();
            // Update existing book fields
            book.setAuthor(newBook.getAuthor());
            book.setIsbn(newBook.getIsbn());
            book.setCopies(newBook.getCopies());
            book.setDescription(newBook.getDescription());
            book.setPrice(newBook.getPrice());
            book.setTitle(newBook.getTitle());
            book.setQuantity(newBook.getQuantity());
            return bookRepository.save(book);
        } else {
            return bookRepository.save(newBook);
        }
    }

    // DELETE a book by id
    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isEmpty()) {
            throw new BookNotFoundException(id);
        }
        bookRepository.deleteById(id);
    }
}

class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(Long id) {
        super("Could not find book " + id);
    }
}

@RestControllerAdvice
class BookNotFoundAdvice {

    @ExceptionHandler(BookNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String bookNotFoundHandler(BookNotFoundException ex) {
        return ex.getMessage();
    }
}