package csd230.lab2.controllers;

import csd230.lab2.entities.Cart;
import csd230.lab2.entities.CartItem;
import csd230.lab2.respositories.CartRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/checkout")
public class CheckoutController {

    private final CartRepository cartRepository;

    public CheckoutController(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    /**
     * Displays the checkout page for the "first" (or only) cart.
     * In a real application, you would fetch the cart by the logged-in user.
     */
    @GetMapping
    public String checkout(Model model) {
        // For simplicity, get the first cart in the database
        Cart cart = cartRepository.findAll().iterator().hasNext()
                ? cartRepository.findAll().iterator().next()
                : null;

        double totalPrice = 0.0;
        if (cart != null) {
            for (CartItem item : cart.getItems()) {
                totalPrice += item.getPrice() * item.getQuantity();
            }
        }

        model.addAttribute("cart", cart);
        model.addAttribute("totalPrice", totalPrice);
        return "checkout";
    }

    /**
     * Handles checkout confirmation (e.g., emptying the cart, saving an order, etc.).
     * In this simplified version, we just redirect back to /cart after checkout.
     */
    @PostMapping("/confirm")
    public String confirmCheckout() {
        // In a real app, you could mark the cart as "purchased," clear items, or do order logic
        // For demonstration, we will just redirect back to the cart
        return "redirect:/cart";
    }
}
