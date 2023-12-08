package com.javaschool.onlineshop.controllers;

import com.javaschool.onlineshop.dto.ShopUserRequestDTO;
import com.javaschool.onlineshop.dto.UserAddressRequestDTO;
import com.javaschool.onlineshop.services.UserAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/userAddress")
@RequiredArgsConstructor
public class UserAddressController {
    private final UserAddressService userAddressService;

    // Endpoint to create a new user address
    @PostMapping
    public ResponseEntity<String> createUserAddress(@RequestBody UserAddressRequestDTO userAddressDTO) {
        UserAddressRequestDTO result = userAddressService.saveUserAddress(userAddressDTO);
        return ResponseEntity.ok("User Address created : " + result.getApartament());
    }

    // Endpoint to get all user addresses
    @GetMapping
    public ResponseEntity<List<UserAddressRequestDTO>> getAllUserAddress(){
        List<UserAddressRequestDTO> result = userAddressService.getAllUserAddress();
        return ResponseEntity.ok(result);
    }

    // Endpoint to get all user addresses for a specific user
    @GetMapping("user/{uuid}")
    public ResponseEntity<List<UserAddressRequestDTO>> getAllUserAddressForUser(@PathVariable UUID uuid) {
        System.out.println(uuid);
        List<UserAddressRequestDTO> result = userAddressService.getAllUserAddressForUser(uuid);
        return ResponseEntity.ok(result);
    }

    // Endpoint to get a user address by UUID
    @GetMapping("/{uuid}")
    public ResponseEntity<UserAddressRequestDTO> getUserAddressByUuid(@PathVariable UUID uuid) {
        UserAddressRequestDTO result = userAddressService.getUserAddressbyUuid(uuid);
        return ResponseEntity.ok(result);
    }

}
