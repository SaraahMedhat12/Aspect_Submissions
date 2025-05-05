//package com.example.demo.controller;
//
//import com.example.demo.dto.JwtResponse;
//import com.example.demo.dto.LoginRequest;
//import com.example.demo.dto.RegisterRequest;
//import com.example.demo.service.AuthService;
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@CrossOrigin(origins = "*", maxAge = 3600)
//@RestController
//@RequestMapping("/api/auth")
//public class AuthController {
//
//    @Autowired
//    private AuthService auth_Service;
//
////    @PostMapping("/login")
////    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
////        return auth_Service.loginUser(loginRequest);
////    }
//    @PostMapping("/login")
//    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
//        // a simple log or check to ensure the method is being called and the credentials are correct.
//        System.out.println("Logging in user: " + loginRequest.getUsername());
//        return auth_Service.loginUser(loginRequest);
//    }
//
//
//    @PostMapping("/register")
//    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
//        return auth_Service.registerUser(registerRequest);
//    }
//}

package com.example.demo.controller;

import com.example.demo.dto.JwtResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.service.Authentication_Service;
import com.example.demo.service.Authentication_Service;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class Authentication_Controller {

    @Autowired
    private Authentication_Service auth_Service;


    @PostMapping("/login")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        System.out.println("Logging in user: " + loginRequest.getUsername());
        return auth_Service.loginUser(loginRequest);
    }


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        return auth_Service.registerUser(registerRequest);
    }


    @GetMapping("/check-authentication")
    public ResponseEntity<String> checkAuthentication() {
        return ResponseEntity.status(HttpStatus.OK).body("You are authenticated successfully!");
    }


    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser() {
        return ResponseEntity.status(HttpStatus.OK).body("You have logged out successfully!");
    }
}
