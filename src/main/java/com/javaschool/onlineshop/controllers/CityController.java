package com.javaschool.onlineshop.controllers;

import com.javaschool.onlineshop.dto.CityRequestDTO;
import com.javaschool.onlineshop.services.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

    @PutMapping("/{uuid}")
    public ResponseEntity<String> updateCity(@PathVariable UUID uuid, @RequestBody CityRequestDTO cityDTO){
        cityService.updateCity(uuid, cityDTO);
        return ResponseEntity.ok("City changed succesfully");
    }
}
