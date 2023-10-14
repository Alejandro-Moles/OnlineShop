package com.javaschool.onlineshop.Controllers;

import com.javaschool.onlineshop.DTO.PostalCodeRequestDTO;
import com.javaschool.onlineshop.Services.PostalCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
