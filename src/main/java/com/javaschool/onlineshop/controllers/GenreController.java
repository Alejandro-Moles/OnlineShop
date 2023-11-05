package com.javaschool.onlineshop.controllers;

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

    @PostMapping
    public ResponseEntity<String> createGenre(@RequestBody GenreRequestDTO genreDTO) {
        GenreRequestDTO result = genreService.saveGenre(genreDTO);
        return ResponseEntity.ok("Genre created : " + result.getType());
    }

    @GetMapping
    public ResponseEntity<List<GenreRequestDTO>> getAllGenres(){
        List<GenreRequestDTO> result = genreService.getAllGenres();
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<String> updateGenre(@PathVariable UUID uuid, @RequestBody GenreRequestDTO genreDTO){
        genreService.updateGenre(uuid, genreDTO);
        return ResponseEntity.ok("Genre changed successfully");
    }
}
