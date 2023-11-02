package com.javaschool.onlineshop.controllers;

import com.javaschool.onlineshop.dto.CityRequestDTO;
import com.javaschool.onlineshop.services.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<CityRequestDTO>> getAllCities(){
        List<CityRequestDTO> result = cityService.getAllCities();
        return ResponseEntity.ok(result);
    }
}
