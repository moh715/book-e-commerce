package com.ecommerce.ecomgrade.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// @ControllerAdvice handles exceptions for Thymeleaf (MVC) controllers.
// It returns HTML pages instead of raw JSON — which is what we want in this app.
@ControllerAdvice
public class GlobalExceptionHandler {

    // Handles our custom "not found" exception
    @ExceptionHandler(ResourceNotFound.class)
    public String handleNotFound(ResourceNotFound ex, Model model) {
        model.addAttribute("errorMsg", ex.getMessage());
        return "error";   // renders templates/error.html
    }

    // Handles our custom "bad request" exception
    @ExceptionHandler(APIException.class)
    public String handleAPIException(APIException ex, Model model) {
        model.addAttribute("errorMsg", ex.getMessage());
        return "error";   // renders templates/error.html
    }

    // Catch-all for any unexpected exception
    @ExceptionHandler(Exception.class)
    public String handleGeneral(Exception ex, Model model) {
        model.addAttribute("errorMsg", "Something went wrong: " + ex.getMessage());
        return "error";
    }
}
