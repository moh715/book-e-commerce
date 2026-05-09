package com.ecommerce.ecomgrade.controller;
import com.ecommerce.ecomgrade.exception.APIException;
import com.ecommerce.ecomgrade.model.*;
import com.ecommerce.ecomgrade.security.SecurityUtils;
import com.ecommerce.ecomgrade.service.*;
import com.ecommerce.ecomgrade.service.impl.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes("cart")
public class CartController {
    @Autowired private IBookService bookService;
    @Autowired private IOrderService orderService;
    @Autowired private EmailService emailService;
    @Autowired private SecurityUtils securityUtils;
    @ModelAttribute("cart")
    public List<OrderItem> cart() { return new ArrayList<>(); }

    @PostMapping("/add-to-cart")
    public String addToCart(@RequestParam Long bookId,
                            @RequestParam(defaultValue="1") Integer quantity,
                            @ModelAttribute("cart") List<OrderItem> cart,
                            RedirectAttributes flash) {
        if (quantity <= 0) throw new APIException("Quantity must be at least 1.");
        Book book = bookService.findEntityById(bookId);
        if (book.getStock() < quantity)
            throw new APIException("Not enough stock for: " + book.getTitle());
        OrderItem existing = cart.stream()
                .filter(i -> i.getBook().getId().equals(bookId)).findFirst().orElse(null);
        if (existing != null) existing.setQuantity(existing.getQuantity() + quantity);
        else { OrderItem item = new OrderItem(); item.setBook(book);
            item.setQuantity(quantity); cart.add(item); }
        flash.addFlashAttribute("successMsg", book.getTitle() + " added to cart.");
        return "redirect:/books";
    }


    @PostMapping("/remove-from-cart")
    public String removeFromCart(@RequestParam int index,
                                 @ModelAttribute("cart") List<OrderItem> cart) {
        if (index >= 0 && index < cart.size()) cart.remove(index);
        return "redirect:/cart";
    }


    @GetMapping("/cart")
    public String viewCart(@ModelAttribute("cart") List<OrderItem> cart, Model model) {
        double total = cart.stream()
                .mapToDouble(i -> i.getBook().getPrice() * i.getQuantity()).sum();
        model.addAttribute("total", total);
        return "cart";
    }


    @PostMapping("/checkout")
    public String checkout(@ModelAttribute("cart") List<OrderItem> cart,
                           RedirectAttributes flash) {
        if (cart.isEmpty()) throw new APIException("Cart is empty.");
        User user = securityUtils.getCurrentUser();
        double total = cart.stream()
                .mapToDouble(i -> i.getBook().getPrice() * i.getQuantity()).sum();
        orderService.createOrder(user, new ArrayList<>(cart), total);
        emailService.sendOrderConfirmation(user.getEmail(), total);
        cart.clear();
        flash.addFlashAttribute("successMsg", "Order placed! Confirmation sent.");
        return "redirect:/orders";
    }


}