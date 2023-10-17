package com.javaschool.onlineshop.controllers;

import com.javaschool.onlineshop.dto.GenreRequestDTO;
import com.javaschool.onlineshop.services.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/genres")
@RequiredArgsConstructor
public class GenreController {
    private final GenreService genreService;

    @PostMapping
    public ResponseEntity<String> createGenre(@RequestBody GenreRequestDTO genreDTO) {
        GenreRequestDTO result = genreService.saveGenre(genreDTO);
        return ResponseEntity.ok("Genre created : " + result.getType());
    }
}
