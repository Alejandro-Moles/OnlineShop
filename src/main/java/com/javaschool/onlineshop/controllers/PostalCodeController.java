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

    @PutMapping("/{uuid}")
    public ResponseEntity<String> updatePostalCode(@PathVariable UUID uuid, @RequestBody PostalCodeRequestDTO postalCodeDTO){
        postalCodeService.updatePostalCode(uuid, postalCodeDTO);
        return ResponseEntity.ok("Postal Code changed succesfully");
    }

    @GetMapping("/availablePostalCodes")
    public ResponseEntity<List<PostalCodeRequestDTO>> getAllAvailablePostalCodes(){
        List<PostalCodeRequestDTO> result = postalCodeService.getAllAvailablePostalCodes();
        return ResponseEntity.ok(result);
    }
}
