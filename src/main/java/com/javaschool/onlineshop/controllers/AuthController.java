package com.javaschool.onlineshop.controllers;

import com.javaschool.onlineshop.dto.AuthResponseDTO;
import com.javaschool.onlineshop.dto.LoginRequestDTO;
import com.javaschool.onlineshop.dto.RegisterRequestDTO;
import com.javaschool.onlineshop.models.ShopUserModel;
import com.javaschool.onlineshop.security.JWTGenerator;
import com.javaschool.onlineshop.services.CartService;
import com.javaschool.onlineshop.services.ShopUserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final CartService cartService;
    private final JWTGenerator jwtGenerator;
    private final ShopUserService shopUserService;

    // Endpoint for user login
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginRequestDTO loginDTO, HttpSession session) {
        // Authenticate the user using Spring Security's AuthenticationManager
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getMail(), loginDTO.getPassword()));
        // Set the authenticated user's information in the security context
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // Generate a JWT token for the authenticated user
        String token = jwtGenerator.generateToken(authentication);

        // Get the current user's email and set it in the cart service
        String userMail = shopUserService.getCurrentUser().getMail();
        cartService.setUserLoggedMail(userMail);
        // Synchronize the user's cart in the session with the user's cart in the database
        cartService.syncCartToUser(session, userMail);

        // Return the JWT token in the response
        return new ResponseEntity<>(new AuthResponseDTO(token), HttpStatus.OK);
    }

    // Endpoint for user registration
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequestDTO registerDto) {
        // Register a new user using the provided registration data
        shopUserService.registerShopUser(registerDto);
        // Return a success message in the response
        return new ResponseEntity<>("User register success", HttpStatus.OK);
    }

    // Endpoint for user logout
    @GetMapping("/logOut")
    public ResponseEntity<String> logOut(HttpSession session) {
        // Remove the user's cart from the session and clear the user's email in the cart service
        session.removeAttribute("cart");
        cartService.setUserLoggedMail(null);
        // Return a success message in the response
        return new ResponseEntity<>("User logout success", HttpStatus.OK);
    }
}

