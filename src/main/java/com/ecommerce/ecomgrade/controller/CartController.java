package com.ecommerce.ecomgrade.controller;

import com.ecommerce.ecomgrade.model.Cart;
import com.ecommerce.ecomgrade.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CartController {

    @Autowired private ICartService cartService;

    @GetMapping("/cart")
    public String viewCart(Model model) {
        Cart cart = cartService.getOrCreateCart();
        double total = cart.getItems().stream()
                .mapToDouble(i -> i.getBook().getPrice() * i.getQuantity())
                .sum();
        model.addAttribute("cart", cart);
        model.addAttribute("total", total);
        return "cart";
    }

    @PostMapping("/add-to-cart")
    public String addToCart(@RequestParam Long bookId,
                            @RequestParam(defaultValue = "1") int quantity,
                            RedirectAttributes flash) {
        cartService.addItem(bookId, quantity);
        flash.addFlashAttribute("successMsg", "Book added to cart.");
        return "redirect:/books";
    }

    @PostMapping("/cart/update")
    public String updateItem(@RequestParam Long cartItemId,
                             @RequestParam int quantity,
                             RedirectAttributes flash) {
        cartService.updateItem(cartItemId, quantity);
        return "redirect:/cart";
    }

    @PostMapping("/cart/remove")
    public String removeItem(@RequestParam Long cartItemId) {
        cartService.removeItem(cartItemId);
        return "redirect:/cart";
    }
}
