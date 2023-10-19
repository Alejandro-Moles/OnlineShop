package com.javaschool.onlineshop.services;

import com.javaschool.onlineshop.dto.GenreRequestDTO;
import com.javaschool.onlineshop.exception.ResourceDuplicate;
import com.javaschool.onlineshop.mapper.GenreMapper;
import com.javaschool.onlineshop.models.Genre;
import com.javaschool.onlineshop.repositories.GenreRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class GenreService {
    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;

    public GenreRequestDTO saveGenre(GenreRequestDTO genreDTO) {
        Genre genre = new Genre();
        genre.setType(genreDTO.getType());
        genre.setIsDeleted(false);

        if (genreRepository.existsByType(genre.getType())) {
            throw new ResourceDuplicate("Genre already exists");
        }

        genreRepository.save(genre);
        return createGenreDTO(genre);
    }

    @Transactional(readOnly = true)
    private GenreRequestDTO createGenreDTO(Genre genre){
        return genreMapper.createGenreDTO(genre);
    }
}
