package com.javaschool.onlineshop.Mapper;

import com.javaschool.onlineshop.DTO.GenreRequestDTO;
import com.javaschool.onlineshop.Models.Genre;
import org.springframework.stereotype.Service;

@Service
public class GenreMapper {
    public GenreRequestDTO createGenreDTO(Genre genre){
        GenreRequestDTO genreDTO= new GenreRequestDTO();
        genreDTO.setType(genre.getType());
        genreDTO.setIsDeleted(genre.getIsDeleted());

        return genreDTO;
    }
}
