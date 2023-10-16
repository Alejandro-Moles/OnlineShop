package com.javaschool.onlineshop.Controllers;

import com.javaschool.onlineshop.DTO.UserAddressRequestDTO;
import com.javaschool.onlineshop.Services.UserAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
