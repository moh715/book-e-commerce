// BookController.java
package com.ecommerce.ecomgrade.controller;
import com.ecommerce.ecomgrade.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BookController {
    @Autowired private IBookService bookService;

    @GetMapping("/books")
    public String books(@RequestParam(required=false) String search, Model model) {
        if (search != null && !search.isBlank()) {
            model.addAttribute("books", bookService.searchBooks(search));
            model.addAttribute("search", search);
        } else {
            model.addAttribute("books", bookService.getAllBooks());
        }
        return "books";
    }
}