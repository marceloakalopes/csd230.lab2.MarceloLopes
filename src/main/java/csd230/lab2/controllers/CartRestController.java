package csd230.lab2.controllers;

import csd230.lab2.entities.Cart;
import csd230.lab2.respositories.CartRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/rest/cart")
public class CartRestController {

    private final CartRepository cartRepository;

    public CartRestController(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    // GET cart by id
    @GetMapping("/{id}")
    public Cart getCart(@PathVariable Long id) {
        Optional<Cart> cart = cartRepository.findById(id);
        if (cart.isPresent()) {
            return cart.get();
        } else {
            throw new CartNotFoundException(id);
        }
    }

    // POST a new cart
    @PostMapping
    public Cart newCart(@RequestBody Cart newCart) {
        return cartRepository.save(newCart);
    }

    // PUT (update) a cart
    @PutMapping("/{id}")
    public Cart replaceCart(@RequestBody Cart newCart, @PathVariable Long id) {
        Optional<Cart> optionalCart = cartRepository.findById(id);
        if (optionalCart.isPresent()) {
            Cart cart = optionalCart.get();
            // Update fields as appropriate.
            // For example, if Cart has a list of items:
            cart.setItems(newCart.getItems());
            return cartRepository.save(cart);
        } else {
            return cartRepository.save(newCart);
        }
    }

    // DELETE a cart by id
    @DeleteMapping("/{id}")
    public void deleteCart(@PathVariable Long id) {
        Optional<Cart> cart = cartRepository.findById(id);
        if (cart.isEmpty()) {
            throw new CartNotFoundException(id);
        }
        cartRepository.deleteById(id);
    }
}

class CartNotFoundException extends RuntimeException {
    public CartNotFoundException(Long id) {
        super("Could not find cart " + id);
    }
}


@RestControllerAdvice
class CartNotFoundAdvice {

    @ExceptionHandler(CartNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String cartNotFoundHandler(CartNotFoundException ex) {
        return ex.getMessage();
    }
}