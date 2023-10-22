package com.javaschool.onlineshop.mapper;

import com.javaschool.onlineshop.dto.GenreRequestDTO;
import com.javaschool.onlineshop.models.Genre;
import org.springframework.stereotype.Service;

@Service
public class GenreMapper {
    public GenreRequestDTO createGenreDTO(Genre genre){
        GenreRequestDTO genreDTO= new GenreRequestDTO();
        genreDTO.setUuid(genre.getGenre_uuid());
        genreDTO.setType(genre.getType());
        genreDTO.setIsDeleted(genre.getIsDeleted());

        return genreDTO;
    }
}
