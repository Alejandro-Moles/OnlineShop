package com.javaschool.onlineshop.mapper;

import com.javaschool.onlineshop.dto.GenreRequestDTO;
import com.javaschool.onlineshop.models.GenreModel;
import org.springframework.stereotype.Service;

@Service
public class GenreMapper {
    public GenreRequestDTO createGenreDTO(GenreModel genre){
        GenreRequestDTO genreDTO= new GenreRequestDTO();
        genreDTO.setUuid(genre.getGenreUuid());
        genreDTO.setType(genre.getType());
        genreDTO.setIsDeleted(genre.getIsDeleted());

        return genreDTO;
    }
}
