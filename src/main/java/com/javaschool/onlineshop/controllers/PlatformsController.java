package com.javaschool.onlineshop.controllers;

import com.javaschool.onlineshop.dto.PlatformsRequestDTO;
import com.javaschool.onlineshop.services.PlatformService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/platforms")
@RequiredArgsConstructor
public class PlatformsController {
    private final PlatformService platformService;

    // Endpoint to create a platform
    @PostMapping
    public ResponseEntity<String> createPlatforms(@RequestBody PlatformsRequestDTO platformsDTO) {
        PlatformsRequestDTO result = platformService.savePlatform(platformsDTO);
        return ResponseEntity.ok("Platform created: " + result.getType());
    }

    // Endpoint to get all platforms
    @GetMapping
    public ResponseEntity<List<PlatformsRequestDTO>> getAllPlatforms() {
        List<PlatformsRequestDTO> result = platformService.getAllPlatforms();
        return ResponseEntity.ok(result);
    }

    // Endpoint to update a platform by UUID
    @PutMapping("/{uuid}")
    public ResponseEntity<String> updatePlatform(@PathVariable UUID uuid, @RequestBody PlatformsRequestDTO platformsDTO) {
        platformService.updatePlatform(uuid, platformsDTO);
        return ResponseEntity.ok("Platform changed successfully");
    }

    // Endpoint to get all available platforms
    @GetMapping("/availablePlatform")
    public ResponseEntity<List<PlatformsRequestDTO>> getAllAvailablePlatforms() {
        List<PlatformsRequestDTO> result = platformService.getAllAvailablePlatforms();
        return ResponseEntity.ok(result);
    }
}
