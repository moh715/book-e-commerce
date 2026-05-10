package com.ecommerce.ecomgrade.controller;

import com.ecommerce.ecomgrade.payload.request.AddressRequest;
import com.ecommerce.ecomgrade.service.IAddressService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/addresses")
public class AddressController {

    @Autowired private IAddressService addressService;

    @GetMapping
    public String listAddresses(Model model) {
        model.addAttribute("addresses", addressService.getAddressesForCurrentUser());
        model.addAttribute("addressRequest", new AddressRequest());
        return "addresses";  // templates/addresses.html
    }

    @PostMapping("/add")
    public String addAddress(@Valid @ModelAttribute AddressRequest req,
                             BindingResult result,
                             Model model,
                             RedirectAttributes flash) {
        if (result.hasErrors()) {
            model.addAttribute("addresses", addressService.getAddressesForCurrentUser());
            return "addresses";
        }
        addressService.addAddress(req);
        flash.addFlashAttribute("successMsg", "Address saved.");
        return "redirect:/addresses";
    }

    @PostMapping("/delete/{id}")
    public String deleteAddress(@PathVariable Long id, RedirectAttributes flash) {
        addressService.deleteAddress(id);
        flash.addFlashAttribute("successMsg", "Address removed.");
        return "redirect:/addresses";
    }
}
