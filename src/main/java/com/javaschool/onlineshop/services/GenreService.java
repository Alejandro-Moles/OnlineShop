package com.javaschool.onlineshop.services;

import com.javaschool.onlineshop.dto.GenreRequestDTO;
import com.javaschool.onlineshop.exception.NoExistData;
import com.javaschool.onlineshop.exception.ResourceDuplicate;
import com.javaschool.onlineshop.mapper.GenreMapper;
import com.javaschool.onlineshop.models.GenreModel;
import com.javaschool.onlineshop.repositories.GenreRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;

    private GenreRequestDTO createGenreDTO(GenreModel genre){
        return genreMapper.createGenreDTO(genre);
    }

    private GenreModel createGenreEntity(GenreRequestDTO genreDTO, GenreModel genre){
        genre.setType(genreDTO.getType());
        genre.setIsDeleted(genreDTO.getIsDeleted());
        return genre;
    }

    @Transactional
    public GenreRequestDTO saveGenre(GenreRequestDTO genreDTO) {
        GenreModel genre = createGenreEntity(genreDTO, new GenreModel());
        if (genreRepository.existsByType(genre.getType())) {
            throw new ResourceDuplicate("Genre already exists");
        }
        genreRepository.save(genre);
        return createGenreDTO(genre);
    }

    @Transactional
    public void updateGenre(UUID uuid, GenreRequestDTO genreDTO){
        GenreModel genre = loadGenre(uuid);
        createGenreEntity(genreDTO, genre);
        genreRepository.save(genre);
    }

    @Transactional(readOnly = true)
    public List<GenreRequestDTO> getAllGenres(){
        return genreRepository.findAll().stream().map(this::createGenreDTO).toList();
    }

    @Transactional(readOnly = true)
    public GenreModel loadGenre(UUID uuid){
        return genreRepository.findById(uuid).orElseThrow(() -> new NoExistData("Genre don't exist"));
    }

    @Transactional(readOnly = true)
    public List<GenreRequestDTO> getAllAvailableGenres(){
        return genreRepository.findAllByIsDeletedFalse().stream().map(this::createGenreDTO).toList();
    }
}
