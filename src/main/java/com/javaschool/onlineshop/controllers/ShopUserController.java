package com.javaschool.onlineshop.controllers;

import com.javaschool.onlineshop.dto.ShopUserRequestDTO;
import com.javaschool.onlineshop.services.ShopUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

    @PutMapping("/{uuid}")
    public ResponseEntity<String> updateShopUser(@PathVariable UUID uuid, @RequestBody ShopUserRequestDTO shopUserDTO){
        shopUserService.updateShopUser(uuid, shopUserDTO);
        return ResponseEntity.ok("Shop user changed successfully");
    }
}
