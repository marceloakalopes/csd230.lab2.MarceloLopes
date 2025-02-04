package csd230.lab2.controllers;

import csd230.lab2.entities.Cart;
import csd230.lab2.entities.CartItem;
import csd230.lab2.respositories.CartItemRepository;
import csd230.lab2.respositories.CartRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    public CartController(CartRepository cartRepository,
                          CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
    }

    // 1) View the Cart (assume single cart for demo)
    @GetMapping
    public String cart(Model model) {
        Cart firstCart = cartRepository.findAll().iterator().next();
        model.addAttribute("cart", firstCart);
        return "cart"; // -> templates/cart.html
    }

    // 2) Add a Cart (for demonstration, if you want multiple)
    @GetMapping("/add-cart")
    public String cartForm(Model model) {
        model.addAttribute("cart", new Cart());
        return "add-cart";
    }

    @PostMapping("/add-cart")
    public String cartSubmit(@ModelAttribute Cart cart) {
        cartRepository.save(cart);
        return "redirect:/cart";
    }

    // 3) Delete CartItem from cart
    @PostMapping("/remove-cartitem")
    public String removeCartItem(@RequestParam("cartItemId") Long id) {
        cartItemRepository.removeById(id);
        return "redirect:/cart";
    }

    // 4) Checkout
    @GetMapping("/checkout")
    public String checkout(Model model) {
        Cart firstCart = cartRepository.findAll().iterator().next();
        double total = 0.0;
        for (CartItem item : firstCart.getItems()) {
            total += (item.getPrice() * item.getQuantity());
        }
        model.addAttribute("cart", firstCart);
        model.addAttribute("totalPrice", total);
        return "checkout"; // -> templates/checkout.html
    }

    // 5) Confirm checkout - just an example
    @PostMapping("/confirm-checkout")
    public String confirmCheckout(Model model) {
        // you can implement final purchase logic or empty the cart here
        Cart cart = cartRepository.findAll().iterator().next();
        // e.g., remove items or mark as purchased
        // cart.getItems().clear();
        // cartRepository.save(cart);
        return "redirect:/cart";
    }
}
