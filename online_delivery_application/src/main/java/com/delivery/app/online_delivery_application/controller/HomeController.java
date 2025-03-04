package com.delivery.app.online_delivery_application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.delivery.app.online_delivery_application.model.User;
import com.delivery.app.online_delivery_application.service.UserService;




@Controller
public class HomeController {

    @Autowired
    UserService userService;

    @RequestMapping("/")
    public String home() {
        return "index";
    }

    @RequestMapping("/login")
    public String login() {
        return "login"; // Maps to login.jsp
    }

    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("user", new User()); // Bind an empty user object for the form
        return "signup"; // Display the signup form (signup.jsp)
    }

    @PostMapping("/signup")
    public String registerUser(@ModelAttribute User user) {
        // user.setRole("CUSTOMER"); // Ensure the role is set to CUSTOMER
        userService.registerUser(user); // Save the new user
        return "redirect:/"; // Redirect to the home page after registration
    }


}

