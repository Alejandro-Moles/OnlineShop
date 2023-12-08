package com.javaschool.onlineshop.controllers;

import com.javaschool.onlineshop.dto.NewPasswordDTO;
import com.javaschool.onlineshop.dto.ProfileDataDTO;
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

    // Endpoint to create a new shop user
    @PostMapping
    public ResponseEntity<String> createShopUser(@RequestBody ShopUserRequestDTO shopUserDTO) {
        ShopUserRequestDTO result = shopUserService.saveShopUser(shopUserDTO);
        return ResponseEntity.ok("ShopUser created with ID: " + result.getMail());
    }

    // Endpoint to get all shop users
    @GetMapping
    public ResponseEntity<List<ShopUserRequestDTO>> getAllShopUser(){
        List<ShopUserRequestDTO> result = shopUserService.getAllShopUser();
        return ResponseEntity.ok(result);
    }

    // Endpoint to update the password of a shop user
    @PutMapping("/{uuid}")
    public ResponseEntity<String> updateShopUserPassword(@PathVariable UUID uuid, @RequestBody NewPasswordDTO passwordDTO){
        shopUserService.updateShopUserPassword(uuid, passwordDTO);
        return ResponseEntity.ok("Shop user changed successfully");
    }

    @PutMapping("profileData/{uuid}")
    public ResponseEntity<String> updateShopUserPassword(@PathVariable UUID uuid, @RequestBody ProfileDataDTO profileDataDTO){
        System.out.println(profileDataDTO);
        shopUserService.updateShopUserData(uuid, profileDataDTO);
        return ResponseEntity.ok("Shop user changed successfully");
    }

    // Endpoint to get the currently authenticated user
    @GetMapping("/actual")
    public ResponseEntity<ShopUserRequestDTO> getCurrentUser(){
        return ResponseEntity.ok(shopUserService.getCurrentUser());
    }

    // Endpoint to get the profile of a shop user by UUID
    @GetMapping("/profile/{uuid}")
    public ResponseEntity<ShopUserRequestDTO> getShopUserbyUuid(@PathVariable UUID uuid){
        ShopUserRequestDTO result = shopUserService.getShopUserbyUuid(uuid);
        return ResponseEntity.ok(result);
    }

    // Endpoint to get statistics of a shop user by email
    @GetMapping("/statistic/{mail}")
    public ResponseEntity<UserStatisticsDTO> getShopUserStatistic(@PathVariable String mail){
        UserStatisticsDTO result = shopUserService.getUserStatistic(mail);
        return ResponseEntity.ok(result);
    }

    // Endpoint to assign roles to a shop user by UUID
    @PutMapping("/assignRoles/{uuid}")
    public ResponseEntity<String> assignRolesToUser(
            @PathVariable UUID uuid,
            @RequestBody List<String> roleTypes
    ) {
        shopUserService.assignRolesToUser(uuid, roleTypes);
        return ResponseEntity.ok("Roles assigned successfully to user with UUID: " + uuid);
    }

}
