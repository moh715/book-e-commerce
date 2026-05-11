package com.ecommerce.ecomgrade.controller;

import com.ecommerce.ecomgrade.exception.APIException;
import com.ecommerce.ecomgrade.model.*;
import com.ecommerce.ecomgrade.payload.request.BookRequest;
import com.ecommerce.ecomgrade.repository.*;
import com.ecommerce.ecomgrade.service.IBookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired private CategoryRepository categoryRepository;
    @Autowired private BookRepository bookRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private OrderRepository orderRepository;
    @Autowired private IBookService bookService;


    @GetMapping
    public String dashboard(Model model) {
        model.addAttribute("totalBooks",    bookRepository.count());
        model.addAttribute("totalUsers",    userRepository.count());
        model.addAttribute("totalOrders",   orderRepository.count());
        model.addAttribute("totalCategories", categoryRepository.count());
        return "admin/dashboard";
    }

    @GetMapping("/categories")
    public String categories(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("newCategory", new Category());
        return "admin/categories";
    }

    @PostMapping("/categories/add")
    public String addCategory(@RequestParam String name, RedirectAttributes flash) {
        if (name == null || name.isBlank())
            throw new APIException("Category name cannot be empty.");
        Category cat = new Category();
        cat.setName(name.trim());
        categoryRepository.save(cat);
        flash.addFlashAttribute("successMsg", "Category added.");
        return "redirect:/admin/categories";
    }

    @PostMapping("/categories/delete/{id}")
    public String deleteCategory(@PathVariable Long id, RedirectAttributes flash) {
        categoryRepository.deleteById(id);
        flash.addFlashAttribute("successMsg", "Category deleted.");
        return "redirect:/admin/categories";
    }


    @GetMapping("/books")
    public String books(Model model) {
        model.addAttribute("books",      bookRepository.findAll());
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("bookRequest", new BookRequest());
        return "admin/books";
    }

    @PostMapping("/books/add")
    public String addBook(@Valid @ModelAttribute BookRequest req,
                          BindingResult result, Model model, RedirectAttributes flash) {
        if (result.hasErrors()) {
            model.addAttribute("books",      bookRepository.findAll());
            model.addAttribute("categories", categoryRepository.findAll());
            return "admin/books";
        }
        bookService.addBook(req);
        flash.addFlashAttribute("successMsg", "Book added.");
        return "redirect:/admin/books";
    }

    @PostMapping("/books/delete/{id}")
    public String deleteBook(@PathVariable Long id, RedirectAttributes flash) {
        bookService.deleteBook(id);
        flash.addFlashAttribute("successMsg", "Book deleted.");
        return "redirect:/admin/books";
    }

    // ── Users ──────────────────────────────────────────────────────────────
    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "admin/users";
    }

    // ── Orders ─────────────────────────────────────────────────────────────
    @GetMapping("/orders")
    public String orders(Model model) {
        model.addAttribute("orders", orderRepository.findAll());
        return "admin/orders";
    }

    @PostMapping("/orders/{id}/status")
    public String updateStatus(@PathVariable Long id,
                               @RequestParam String status,
                               RedirectAttributes flash) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new APIException("Order not found."));
        order.setStatus(status);
        orderRepository.save(order);
        flash.addFlashAttribute("successMsg", "Order status updated.");
        return "redirect:/admin/orders";
    }
}
