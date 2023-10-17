package com.javaschool.onlineshop.controllers;

import com.javaschool.onlineshop.dto.StatusRequestDTO;
import com.javaschool.onlineshop.services.StatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/status")
@RequiredArgsConstructor
public class StatusController {
    private final StatusService statusService;

    @PostMapping
    public ResponseEntity<String> createStatus(@RequestBody StatusRequestDTO statusDTO) {
        StatusRequestDTO result = statusService.saveStatus(statusDTO);
        return ResponseEntity.ok("Status created : " + result.getType());
    }
}
