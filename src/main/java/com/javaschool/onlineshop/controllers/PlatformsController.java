package com.javaschool.onlineshop.controllers;

import com.javaschool.onlineshop.dto.PlatformsRequestDTO;
import com.javaschool.onlineshop.services.PlatformService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/platforms")
@RequiredArgsConstructor
public class PlatformsController {
    private final PlatformService platformService;

    @PostMapping
    public ResponseEntity<String> createPlatforms(@RequestBody PlatformsRequestDTO platformsDTO) {
        PlatformsRequestDTO result = platformService.savePlatform(platformsDTO);
        return ResponseEntity.ok("Platform created : " + result.getType());
    }
}
