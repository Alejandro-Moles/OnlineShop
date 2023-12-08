package com.javaschool.onlineshop.controllers;

import com.javaschool.onlineshop.dto.CategoryRequestDTO;
import com.javaschool.onlineshop.dto.GenreRequestDTO;
import com.javaschool.onlineshop.services.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/genres")
@RequiredArgsConstructor
public class GenreController {
    private final GenreService genreService;

    // Endpoint to create a new genre
    @PostMapping
    public ResponseEntity<String> createGenre(@RequestBody GenreRequestDTO genreDTO) {
        GenreRequestDTO result = genreService.saveGenre(genreDTO);
        return ResponseEntity.ok("Genre created : " + result.getType());
    }

    // Endpoint to get all genres
    @GetMapping
    public ResponseEntity<List<GenreRequestDTO>> getAllGenres() {
        List<GenreRequestDTO> result = genreService.getAllGenres();
        return ResponseEntity.ok(result);
    }

    // Endpoint to update an existing genre by UUID
    @PutMapping("/{uuid}")
    public ResponseEntity<String> updateGenre(@PathVariable UUID uuid, @RequestBody GenreRequestDTO genreDTO) {
        genreService.updateGenre(uuid, genreDTO);
        return ResponseEntity.ok("Genre changed successfully");
    }

    // Endpoint to get all available genres
    @GetMapping("/availableGenres")
    public ResponseEntity<List<GenreRequestDTO>> getAllAvailableGenres() {
        List<GenreRequestDTO> result = genreService.getAllAvailableGenres();
        return ResponseEntity.ok(result);
    }
}
