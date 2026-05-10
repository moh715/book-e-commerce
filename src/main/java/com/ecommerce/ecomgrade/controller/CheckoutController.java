package com.ecommerce.ecomgrade.controller;

import com.ecommerce.ecomgrade.model.*;
import com.ecommerce.ecomgrade.payload.request.CheckoutRequest;
import com.ecommerce.ecomgrade.repository.AddressRepository;
import com.ecommerce.ecomgrade.security.SecurityUtils;
import com.ecommerce.ecomgrade.service.*;
import com.ecommerce.ecomgrade.service.impl.EmailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/checkout")
public class CheckoutController {

    @Autowired private ICartService cartService;
    @Autowired private IOrderService orderService;
    @Autowired private IAddressService addressService;
    @Autowired private AddressRepository addressRepository;
    @Autowired private SecurityUtils securityUtils;
    @Autowired private EmailService emailService;

    // Step 1: Show checkout page — cart summary + address selector
    @GetMapping
    public String checkoutPage(Model model) {
        Cart cart = cartService.getOrCreateCart();
        if (cart.getItems().isEmpty()) return "redirect:/cart";

        double total = cart.getItems().stream()
                .mapToDouble(i -> i.getBook().getPrice() * i.getQuantity()).sum();

        model.addAttribute("cart", cart);
        model.addAttribute("total", total);
        model.addAttribute("addresses", addressService.getAddressesForCurrentUser());
        model.addAttribute("checkoutRequest", new CheckoutRequest());
        return "checkout";   // templates/checkout.html
    }

    // Step 2: Confirm order
    @PostMapping("/confirm")
    public String confirmOrder(@Valid @ModelAttribute CheckoutRequest req,
                               BindingResult result,
                               Model model,
                               RedirectAttributes flash) {
        if (result.hasErrors()) {
            Cart cart = cartService.getOrCreateCart();
            model.addAttribute("cart", cart);
            model.addAttribute("addresses", addressService.getAddressesForCurrentUser());
            return "checkout";
        }

        User user = securityUtils.getCurrentUser();
        Cart cart = cartService.getOrCreateCart();
        Address address = addressRepository.findById(req.getAddressId())
                .orElseThrow(() -> new RuntimeException("Address not found"));

        orderService.createOrder(user, cart, address);
        emailService.sendOrderConfirmation(user.getEmail(), cart.getItems().stream()
                .mapToDouble(i -> i.getBook().getPrice() * i.getQuantity()).sum());
        cartService.clearCart();

        flash.addFlashAttribute("successMsg", "Order placed successfully!");
        return "redirect:/orders";
    }
}
