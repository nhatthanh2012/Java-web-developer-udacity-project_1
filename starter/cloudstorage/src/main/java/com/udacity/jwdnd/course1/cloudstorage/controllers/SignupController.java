package com.udacity.jwdnd.course1.cloudstorage.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import com.udacity.jwdnd.course1.cloudstorage.entities.Users;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class SignupController {
    @Autowired
    private UserService userService;

    @GetMapping("/signup")
    public String getSignupPage() {
        return "signup"; // return signup.html
    }

    @PostMapping("/submit-signup")
    // comment out by ThanhTLN
    public String submitSignup(@ModelAttribute Users users, Model model) {
        // comment out  by ThanhTln
        String signupErrorMsg = "";
        // check user exist
        if (!userService.isUserExistInDb(users.getUsername())) {
            signupErrorMsg = "The username already exists.";
        } else {
            // add new user sigin
            int rowsNewUserAdded =  userService.signupNewUser(users);

            // error when add to db
            if (rowsNewUserAdded < 0) {
                signupErrorMsg = "There was an error signing you up. Please try again.";
            }
        }

        if (signupErrorMsg == "") {
            model.addAttribute("signupStatus", true);
        } else {
            model.addAttribute("signupStatus", false);
            model.addAttribute("signupErrorMsg", signupErrorMsg);
        }

        return "redirect:/login";
    }
}
