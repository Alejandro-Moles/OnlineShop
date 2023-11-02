package com.javaschool.onlineshop.controllers;

import com.javaschool.onlineshop.dto.GenreRequestDTO;
import com.javaschool.onlineshop.dto.UserAddressRequestDTO;
import com.javaschool.onlineshop.services.UserAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/userAddress")
@RequiredArgsConstructor
public class UserAddressController {
    private final UserAddressService userAddressService;

    @PostMapping
    public ResponseEntity<String> createUserAddress(@RequestBody UserAddressRequestDTO userAddressDTO) {
        UserAddressRequestDTO result = userAddressService.saveUserAddress(userAddressDTO);
        return ResponseEntity.ok("User Address created : " + result.getApartament());
    }

    @GetMapping
    public ResponseEntity<List<UserAddressRequestDTO>> getAllUserAddress(){
        List<UserAddressRequestDTO> result = userAddressService.getAllUserAddress();
        return ResponseEntity.ok(result);
    }
}
