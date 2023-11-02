package com.javaschool.onlineshop.services;

import com.javaschool.onlineshop.dto.GenreRequestDTO;
import com.javaschool.onlineshop.exception.ResourceDuplicate;
import com.javaschool.onlineshop.mapper.GenreMapper;
import com.javaschool.onlineshop.models.Genre;
import com.javaschool.onlineshop.repositories.GenreRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class GenreService {
    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;

    public GenreRequestDTO saveGenre(GenreRequestDTO genreDTO) {
        Genre genre = createGenreEntity(genreDTO, new Genre());
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

    private Genre createGenreEntity(GenreRequestDTO genreDTO, Genre genre){
        genre.setType(genreDTO.getType());
        genre.setIsDeleted(false);
        return genre;
    }

    @Transactional(readOnly = true)
    public List<GenreRequestDTO> getAllGenres(){
        return genreRepository.findAll().stream().map(this::createGenreDTO).toList();
    }
}
