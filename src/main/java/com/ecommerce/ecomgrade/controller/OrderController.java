// OrderController.java
package com.ecommerce.ecomgrade.controller;
import com.ecommerce.ecomgrade.model.User;
import com.ecommerce.ecomgrade.security.SecurityUtils;
import com.ecommerce.ecomgrade.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class OrderController {
    @Autowired private IOrderService orderService;
    @Autowired private SecurityUtils securityUtils;

    @GetMapping("/orders")
    public String viewOrders(Model model) {
        User user = securityUtils.getCurrentUser();
        model.addAttribute("orders", orderService.getOrdersByUser(user));
        return "orders";
    }
}