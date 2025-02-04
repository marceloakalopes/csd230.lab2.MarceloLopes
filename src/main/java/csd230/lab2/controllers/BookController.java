package csd230.lab2.controllers;

import csd230.lab2.entities.Book;
import csd230.lab2.entities.Cart;
import csd230.lab2.respositories.BookRepository;
import csd230.lab2.respositories.CartRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookRepository bookRepository;
    private final CartRepository cartRepository;

    public BookController(BookRepository bookRepository,
                          CartRepository cartRepository) {
        this.bookRepository = bookRepository;
        this.cartRepository = cartRepository;
    }

    // 1) View all Books
    @GetMapping
    public String books(Model model) {
        model.addAttribute("books", bookRepository.findAll());
        return "books"; // -> templates/books.html
    }

    // 2) Add a Book (GET + POST)
    @GetMapping("/add-book")
    public String bookForm(Model model) {
        model.addAttribute("book", new Book());
        return "add-book"; // -> templates/add-book.html
    }

    @PostMapping("/add-book")
    public String bookSubmit(@ModelAttribute Book book, Model model) {
        // you can update description or other fields before saving
        book.setDescription("Book: " + book.getTitle());
        bookRepository.save(book);
        return "redirect:/books";
    }

    // 3) Edit a Book (GET + POST)
    @GetMapping("/edit-book")
    public String editBook(@RequestParam("id") Long id, Model model) {
        Optional<Book> book = bookRepository.findById(id);
        model.addAttribute("book", book);
        return "edit-book";
    }

    @PostMapping("/edit-book")
    public String editBookSubmit(@ModelAttribute Book book) {
        book.setDescription("Book: " + book.getTitle());
        bookRepository.save(book);
        return "redirect:/books";
    }

    // 4) Delete a Book
    @GetMapping("/delete-book")
    public String deleteBook(@RequestParam("id") Long id) {
        bookRepository.deleteById(id);
        return "redirect:/books";
    }

    // 5) Add to Cart - Using checkboxes from the UI
    @PostMapping("/add-to-cart")
    public String addBooksToCart(@RequestParam("selectedBooks") List<Long> selectedBookIds) {
        // In a real application, you would identify which cart belongs to the user
        // For demonstration, just pick the first cart or create a new one
        Cart cart = cartRepository.findAll().iterator().next();
        // If empty, create new:
        // Cart cart = new Cart();
        // cartRepository.save(cart);

        for (Long bookId : selectedBookIds) {
            Optional<Book> book = bookRepository.findById(bookId);
            // We can add Book (which is a CartItem subclass) to the cart
            cart.addItem(book.get());
        }
        cartRepository.save(cart);

        return "redirect:/cart";
    }
}
