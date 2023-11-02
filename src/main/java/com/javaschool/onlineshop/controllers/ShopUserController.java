package com.javaschool.onlineshop.controllers;

import com.javaschool.onlineshop.dto.GenreRequestDTO;
import com.javaschool.onlineshop.dto.ShopUserRequestDTO;
import com.javaschool.onlineshop.services.ShopUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shopUsers")
@RequiredArgsConstructor
public class ShopUserController {
    private final ShopUserService shopUserService;
    @PostMapping
    public ResponseEntity<String> createShopUser(@RequestBody ShopUserRequestDTO shopUserDTO) {
        ShopUserRequestDTO result = shopUserService.saveShopUser(shopUserDTO);
        return ResponseEntity.ok("ShopUser created with ID: " + result.getMail());
    }

    @GetMapping
    public ResponseEntity<List<ShopUserRequestDTO>> getAllShopUser(){
        List<ShopUserRequestDTO> result = shopUserService.getAllShopUser();
        return ResponseEntity.ok(result);
    }
}
