package com.javaschool.onlineshop.controllers;

import com.javaschool.onlineshop.dto.NewPasswordDTO;
import com.javaschool.onlineshop.dto.ProductRequestDTO;
import com.javaschool.onlineshop.dto.ShopUserRequestDTO;
import com.javaschool.onlineshop.dto.UserStatisticsDTO;
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
    public ResponseEntity<String> updateShopUser(@PathVariable UUID uuid, @RequestBody NewPasswordDTO passwordDTO){
        shopUserService.updateShopUser(uuid, passwordDTO);
        return ResponseEntity.ok("Shop user changed successfully");
    }

    @GetMapping("/actual")
    public ResponseEntity<ShopUserRequestDTO>getCurrentUser(){
        return ResponseEntity.ok(shopUserService.getCurrentUser());
    }

    @GetMapping("/profile/{uuid}")
    public ResponseEntity<ShopUserRequestDTO> getShopUserbyUuid(@PathVariable UUID uuid){
        ShopUserRequestDTO result = shopUserService.getShopUserbyUuid(uuid);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/statistic/{mail}")
    public ResponseEntity<UserStatisticsDTO> getShopUserStatistic(@PathVariable String mail){
        UserStatisticsDTO result = shopUserService.getUserStatistic(mail);
        return ResponseEntity.ok(result);
    }

}
