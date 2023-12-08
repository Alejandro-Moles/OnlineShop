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

    // Injecting the GenreRepository for database operations
    private final GenreRepository genreRepository;

    // Injecting the GenreMapper for mapping between DTO and entity
    private final GenreMapper genreMapper;

    // This method creates a GenreDTO from a GenreModel
    private GenreRequestDTO createGenreDTO(GenreModel genre) {
        return genreMapper.createGenreDTO(genre);
    }

    // This method updates a GenreModel entity with data from a GenreDTO
    private GenreModel createGenreEntity(GenreRequestDTO genreDTO, GenreModel genre) {
        genre.setType(genreDTO.getType());
        genre.setIsDeleted(genreDTO.getIsDeleted());
        return genre;
    }

    // This method saves a new genre to the database
    @Transactional
    public GenreRequestDTO saveGenre(GenreRequestDTO genreDTO) {
        // Creating a GenreModel entity from the DTO
        GenreModel genre = createGenreEntity(genreDTO, new GenreModel());

        // Checking if a genre with the same type already exists
        if (genreRepository.existsByType(genre.getType())) {
            throw new ResourceDuplicate("Genre already exists");
        }

        // Saving the genre to the database
        genreRepository.save(genre);

        // Returning the saved genre as a DTO
        return createGenreDTO(genre);
    }

    // This method updates an existing genre in the database
    @Transactional
    public void updateGenre(UUID uuid, GenreRequestDTO genreDTO) {
        // Loading the existing genre from the database
        GenreModel genre = loadGenre(uuid);

        if (genreRepository.existsByType(genreDTO.getType())) {
            throw new ResourceDuplicate("Genre already exists in the database");
        }

        // Updating the genre entity with data from the DTO
        createGenreEntity(genreDTO, genre);

        // Saving the updated genre to the database
        genreRepository.save(genre);
    }

    // This method retrieves all genres from the database
    @Transactional(readOnly = true)
    public List<GenreRequestDTO> getAllGenres() {
        return genreRepository.findAll().stream().map(this::createGenreDTO).toList();
    }

    // This method loads a genre by its UUID from the database
    @Transactional(readOnly = true)
    public GenreModel loadGenre(UUID uuid) {
        return genreRepository.findById(uuid).orElseThrow(() -> new NoExistData("Genre doesn't exist"));
    }

    // This method retrieves all available genres (not marked as deleted) from the database
    @Transactional(readOnly = true)
    public List<GenreRequestDTO> getAllAvailableGenres() {
        return genreRepository.findAllByIsDeletedFalse().stream().map(this::createGenreDTO).toList();
    }
}
