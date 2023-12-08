package com.javaschool.onlineshop.controllers;

import com.javaschool.onlineshop.dto.PlatformsRequestDTO;
import com.javaschool.onlineshop.dto.PostalCodeRequestDTO;
import com.javaschool.onlineshop.services.PostalCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/postalCodes")
@RequiredArgsConstructor
public class PostalCodeController {
    private final PostalCodeService postalCodeService;

    // Endpoint to create a postal code
    @PostMapping
    public ResponseEntity<String> createPostalCode(@RequestBody PostalCodeRequestDTO postalCodeDTO) {
        PostalCodeRequestDTO result = postalCodeService.savePostalCode(postalCodeDTO);
        return ResponseEntity.ok("Postal code created with: " + result.getContent());
    }

    // Endpoint to get all postal codes
    @GetMapping
    public ResponseEntity<List<PostalCodeRequestDTO>> getAllPostalCode() {
        List<PostalCodeRequestDTO> result = postalCodeService.getAllPostalCodes();
        return ResponseEntity.ok(result);
    }

    // Endpoint to update a postal code by UUID
    @PutMapping("/{uuid}")
    public ResponseEntity<String> updatePostalCode(@PathVariable UUID uuid, @RequestBody PostalCodeRequestDTO postalCodeDTO) {
        postalCodeService.updatePostalCode(uuid, postalCodeDTO);
        return ResponseEntity.ok("Postal Code changed successfully");
    }

    // Endpoint to get all available postal codes
    @GetMapping("/availablePostalCodes")
    public ResponseEntity<List<PostalCodeRequestDTO>> getAllAvailablePostalCodes() {
        List<PostalCodeRequestDTO> result = postalCodeService.getAllAvailablePostalCodes();
        return ResponseEntity.ok(result);
    }
}
