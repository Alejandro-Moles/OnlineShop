package com.javaschool.onlineshop.Controllers;

import com.javaschool.onlineshop.DTO.CountryRequestDTO;
import com.javaschool.onlineshop.Services.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/countries")
@RequiredArgsConstructor
public class CountryController {
    private final CountryService countryService;

    @PostMapping
    public ResponseEntity<String> createCountry(@RequestBody CountryRequestDTO countryDTO) {
        CountryRequestDTO result = countryService.saveContry(countryDTO);
        return ResponseEntity.ok("Country created with : " + result.getName());
    }
}
