package com.javaschool.onlineshop.controllers;

import com.javaschool.onlineshop.dto.AuthResponseDTO;
import com.javaschool.onlineshop.dto.LoginRequestDTO;
import com.javaschool.onlineshop.dto.RegisterRequestDTO;
import com.javaschool.onlineshop.mapper.ShopUserMapper;
import com.javaschool.onlineshop.models.Role;
import com.javaschool.onlineshop.models.ShopUser;
import com.javaschool.onlineshop.repositories.RoleRepository;
import com.javaschool.onlineshop.repositories.ShopUserRepository;
import com.javaschool.onlineshop.security.JWTGenerator;
import com.javaschool.onlineshop.services.ShopUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import java.util.Collections;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final ShopUserRepository shopUserRepository;
    private final RoleRepository roleRepository;
    private final ShopUserMapper shopUserMapper;
    private final JWTGenerator jwtGenerator;
    private final ShopUserService shopUserService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginRequestDTO loginDTO){
        Authentication authentification = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getMail(),
                loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentification);
        String token = jwtGenerator.generateToken(authentification);
        return  new ResponseEntity<>(new AuthResponseDTO(token), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequestDTO registerDto){
        shopUserService.registerShopUser(registerDto);
        return new ResponseEntity<>("User register success", HttpStatus.OK);
    }
}
