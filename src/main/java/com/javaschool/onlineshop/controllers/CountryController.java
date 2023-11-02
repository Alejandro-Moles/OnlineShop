package com.javaschool.onlineshop.controllers;

import com.javaschool.onlineshop.dto.CountryRequestDTO;
import com.javaschool.onlineshop.services.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/countries")
@RequiredArgsConstructor
public class CountryController {
    private final CountryService countryService;

    @PostMapping
    public ResponseEntity<String> createCountry(@RequestBody CountryRequestDTO countryDTO) {
        CountryRequestDTO result = countryService.saveCountry(countryDTO);
        return ResponseEntity.ok("Country created with : " + result.getName());
    }

    @GetMapping
    public ResponseEntity<List<CountryRequestDTO>> getAllCountries(){
        List<CountryRequestDTO> result = countryService.getAllCountries();
        return ResponseEntity.ok(result);
    }
}
