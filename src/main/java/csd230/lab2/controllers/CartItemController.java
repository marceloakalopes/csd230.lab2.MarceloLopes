package csd230.lab2.controllers;

import csd230.lab2.entities.Cart;
import csd230.lab2.entities.CartItem;
import csd230.lab2.respositories.CartItemRepository;
import csd230.lab2.respositories.CartRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart-items")
public class CartItemController {

    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;

    public CartItemController(CartItemRepository cartItemRepository,
                              CartRepository cartRepository) {
        this.cartItemRepository = cartItemRepository;
        this.cartRepository = cartRepository;
    }

    // 1) View all cart items
    @GetMapping
    public String listCartItems(Model model) {
        model.addAttribute("items", cartItemRepository.findAll());
        return "cart-items";
    }

    // 2) Add a cart item
    @GetMapping("/add")
    public String addCartItemForm(Model model) {
        model.addAttribute("cartItem", new CartItem());
        return "add-cart-item";
    }

    @PostMapping("/add")
    public String addCartItemSubmit(@ModelAttribute CartItem cartItem) {
        // Optionally set which cart it belongs to
        Cart firstCart = cartRepository.findAll().iterator().next();
        cartItem.setCart(firstCart);
        cartItemRepository.save(cartItem);
        return "redirect:/cart-items";
    }

    // 3) Edit cart item
    @GetMapping("/edit")
    public String editCartItem(@RequestParam("id") Long id, Model model) {
        CartItem item = cartItemRepository.findById(id).orElse(null);
        model.addAttribute("cartItem", item);
        return "edit-cart-item";
    }

    @PostMapping("/edit")
    public String editCartItemSubmit(@ModelAttribute CartItem cartItem) {
        cartItemRepository.save(cartItem);
        return "redirect:/cart-items";
    }

    // 4) Delete cart item
    @GetMapping("/delete")
    public String deleteCartItem(@RequestParam("id") Long id) {
        cartItemRepository.deleteById(id);
        return "redirect:/cart-items";
    }
}
