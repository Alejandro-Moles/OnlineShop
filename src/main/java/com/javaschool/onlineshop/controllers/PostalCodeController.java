package com.javaschool.onlineshop.controllers;

import com.javaschool.onlineshop.dto.PostalCodeRequestDTO;
import com.javaschool.onlineshop.services.PostalCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/postalCodes")
@RequiredArgsConstructor
public class PostalCodeController {
    private final PostalCodeService postalCodeService;

    @PostMapping
    public ResponseEntity<String> createPostalCode(@RequestBody PostalCodeRequestDTO postalCodeDTO) {
        PostalCodeRequestDTO result = postalCodeService.savePostalCode(postalCodeDTO);
        return ResponseEntity.ok("Postalcode created with : " + result.getContent());
    }

    @GetMapping
    public ResponseEntity<List<PostalCodeRequestDTO>> getAllPostalCode(){
        List<PostalCodeRequestDTO> result = postalCodeService.getAllPostalCodes();
        return ResponseEntity.ok(result);
    }
}
