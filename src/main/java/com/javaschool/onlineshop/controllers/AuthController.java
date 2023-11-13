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

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginRequestDTO loginDTO, HttpSession session){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getMail(),
                loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);

        String userMail = shopUserService.getCurrentUser().getMail();
        cartService.setUserLoggedMail(userMail);
        cartService.syncCartToUser(session, userMail);

        return  new ResponseEntity<>(new AuthResponseDTO(token), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequestDTO registerDto){
        shopUserService.registerShopUser(registerDto);
        return new ResponseEntity<>("User register success", HttpStatus.OK);
    }

    @GetMapping("/logOut")
    public ResponseEntity<String> logOut(HttpSession session){
        session.removeAttribute("cart");
        cartService.setUserLoggedMail(null);
        return new ResponseEntity<>("User register success", HttpStatus.OK);
    }
}
