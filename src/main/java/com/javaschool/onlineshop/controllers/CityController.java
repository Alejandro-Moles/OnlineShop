package com.javaschool.onlineshop.controllers;

import com.javaschool.onlineshop.dto.CityRequestDTO;
import com.javaschool.onlineshop.services.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cities")
@RequiredArgsConstructor
public class CityController {
    private final CityService cityService;

    @PostMapping
    public ResponseEntity<String> createCity(@RequestBody CityRequestDTO cityDTO) {
        CityRequestDTO result = cityService.saveCity(cityDTO);
        return ResponseEntity.ok("City created with ID: " + result.getName());
    }
}
