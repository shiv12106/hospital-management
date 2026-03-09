package com.hms.hospital.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
// no additional imports required

@Controller
public class HomeController {

    // registration handled by RegistrationController

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

}

