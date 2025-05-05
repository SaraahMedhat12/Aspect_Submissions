package com.example.demo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class Home_Controller {

    @GetMapping("/hello")
    @PreAuthorize("isAuthenticated()")

    public String hello() {

        return "Hello from protected endpoint!";
    }

    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

}