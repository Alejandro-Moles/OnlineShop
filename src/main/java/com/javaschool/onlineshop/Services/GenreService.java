package com.javaschool.onlineshop.Services;

import com.javaschool.onlineshop.DTO.GenreRequestDTO;
import com.javaschool.onlineshop.DTO.PlatformsRequestDTO;
import com.javaschool.onlineshop.Mapper.GenreMapper;
import com.javaschool.onlineshop.Models.Genre;
import com.javaschool.onlineshop.Models.Platforms;
import com.javaschool.onlineshop.Repositories.GenreRepository;
import jakarta.transaction.Transactional;
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

        genreRepository.save(genre);
        return createGenreDTO(genre);
    }

    private GenreRequestDTO createGenreDTO(Genre genre){
        return genreMapper.createGenreDTO(genre);
    }
}
