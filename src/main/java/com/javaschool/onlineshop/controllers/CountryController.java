package com.javaschool.onlineshop.controllers;

import com.javaschool.onlineshop.dto.CountryRequestDTO;
import com.javaschool.onlineshop.services.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/countries")
@RequiredArgsConstructor
public class CountryController {
    private final CountryService countryService;

    // Endpoint to create a new country
    @PostMapping
    public ResponseEntity<String> createCountry(@RequestBody CountryRequestDTO countryDTO) {
        CountryRequestDTO result = countryService.saveCountry(countryDTO);
        return ResponseEntity.ok("Country created with : " + result.getName());
    }

    // Endpoint to get all countries
    @GetMapping
    public ResponseEntity<List<CountryRequestDTO>> getAllCountries() {
        List<CountryRequestDTO> result = countryService.getAllCountries();
        return ResponseEntity.ok(result);
    }

    // Endpoint to update an existing country by UUID
    @PutMapping("/{uuid}")
    public ResponseEntity<String> updateCountry(@PathVariable UUID uuid, @RequestBody CountryRequestDTO countryDTO) {
        countryService.updateCountry(uuid, countryDTO);
        return ResponseEntity.ok("Country changed successfully");
    }
}
