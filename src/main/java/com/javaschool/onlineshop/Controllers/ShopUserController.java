package com.javaschool.onlineshop.Controllers;

import com.javaschool.onlineshop.DTO.CityRequestDTO;
import com.javaschool.onlineshop.DTO.ShopUserRequestDTO;
import com.javaschool.onlineshop.Services.ShopUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
