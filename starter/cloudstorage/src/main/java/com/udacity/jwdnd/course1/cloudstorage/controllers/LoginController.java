package com.udacity.jwdnd.course1.cloudstorage.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

@Controller

public class LoginController {
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    // comment out by ThanhTLN
    public String getLoginPage() {
        return "login"; // return login.html
    }

    @PostMapping("/logout")
    // comment out by ThanhTLN
    public String logout(Model model) {
        // handle logout
        model.addAttribute("logoutStatus", true);
        return "redirect:/login"; // redirect to login.html after logout
    }
}
