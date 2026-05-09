package com.ecommerce.ecomgrade.controller;
import com.ecommerce.ecomgrade.exception.APIException;
import com.ecommerce.ecomgrade.payload.request.RegisterRequest;
import com.ecommerce.ecomgrade.service.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class AuthController {
    @Autowired private IUserService userService;
    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("registerRequest", new RegisterRequest());
        return "register";

    }


    @PostMapping("/register")
    public String registerUser(
            @Valid @ModelAttribute("registerRequest") RegisterRequest req,
            BindingResult result,
            RedirectAttributes flash,
            Model model) {
        if (result.hasErrors()) return "register";
        try {
            userService.registerUser(req);
            flash.addFlashAttribute("successMsg", "Account created! Please log in.");
            return "redirect:/login";
        } catch (APIException ex) {
            model.addAttribute("errorMsg", ex.getMessage());
            return "register";
        }

    }


    @GetMapping("/login")
    public String loginPage(
            @RequestParam(required=false) String error,
            @RequestParam(required=false) String logout,
            Model model) {
        if (error != null) model.addAttribute("errorMsg", "Invalid email or password.");
        if (logout != null) model.addAttribute("successMsg", "Logged out successfully.");
        return "login";
    }





}