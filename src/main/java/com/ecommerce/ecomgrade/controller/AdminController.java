package com.ecommerce.ecomgrade.controller;

import com.ecommerce.ecomgrade.payload.request.BookRequest;
import com.ecommerce.ecomgrade.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private IBookService bookService;

    @GetMapping("/books")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminBooks(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "admin-books";  // You'll need to create this template
    }

    @PostMapping("/books")
    @PreAuthorize("hasRole('ADMIN')")
    public String addBook(@ModelAttribute BookRequest request, RedirectAttributes redirectAttributes) {
        bookService.addBook(request);
        redirectAttributes.addFlashAttribute("successMsg", "Book added successfully!");
        return "redirect:/admin/books";
    }

    // Add more methods for edit/delete as needed
}
