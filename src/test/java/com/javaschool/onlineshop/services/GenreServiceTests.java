package com.javaschool.onlineshop.services;
import com.javaschool.onlineshop.dto.GenreRequestDTO;
import com.javaschool.onlineshop.exception.NoExistData;
import com.javaschool.onlineshop.exception.ResourceDuplicate;
import com.javaschool.onlineshop.mapper.GenreMapper;
import com.javaschool.onlineshop.models.GenreModel;
import com.javaschool.onlineshop.repositories.GenreRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class GenreServiceTests {
    private final GenreRepository genreRepositoryMock = Mockito.mock(GenreRepository.class);
    private final GenreMapper genreMapperMock = Mockito.mock(GenreMapper.class);
    private final GenreService genreService = new GenreService(genreRepositoryMock, genreMapperMock);

    @Test
    void saveGenre_WhenGenreDoesNotExist_ShouldSaveGenre() {
        when(genreMapperMock.createGenreDTO(any())).thenReturn(new GenreRequestDTO());
        when(genreRepositoryMock.existsByType(any())).thenReturn(false);

        GenreRequestDTO genreDTO = new GenreRequestDTO();
        genreDTO.setType("New Genre");
        genreDTO.setUuid(UUID.randomUUID());
        genreDTO.setIsDeleted(false);
        GenreRequestDTO savedGenreDTO = genreService.saveGenre(genreDTO);

        assertNotNull(savedGenreDTO);
    }

    @Test
    void saveGenre_WhenGenreExists_ShouldThrowResourceDuplicateException() {
        when(genreRepositoryMock.existsByType(any())).thenReturn(true);

        GenreRequestDTO genreDTO = new GenreRequestDTO();
        genreDTO.setType("Existing Genre");

        assertThrows(ResourceDuplicate.class, () -> genreService.saveGenre(genreDTO));
    }

    @Test
    void updateGenre_WhenGenreExists_ShouldUpdateGenre() {
        UUID existingGenreUUID = UUID.randomUUID();
        GenreRequestDTO updatedGenreDTO = new GenreRequestDTO();
        updatedGenreDTO.setType("Updated Genre");
        updatedGenreDTO.setUuid(existingGenreUUID);
        updatedGenreDTO.setIsDeleted(false);

        GenreModel existingGenre = new GenreModel();
        existingGenre.setGenreUuid(existingGenreUUID);
        existingGenre.setType("Existing Genre");
        existingGenre.setIsDeleted(false);

        when(genreRepositoryMock.findById(existingGenreUUID)).thenReturn(Optional.of(existingGenre));

        genreService.updateGenre(existingGenreUUID, updatedGenreDTO);

        verify(genreRepositoryMock).save(any(GenreModel.class));

        assertEquals("Updated Genre", existingGenre.getType());
    }

    @Test
    void loadGenre_WhenGenreExists_ShouldReturnGenre() {
        UUID existingGenreUUID = UUID.randomUUID();

        GenreModel existingGenre = new GenreModel();
        existingGenre.setGenreUuid(existingGenreUUID);
        existingGenre.setType("Existing Genre");
        existingGenre.setIsDeleted(false);

        when(genreRepositoryMock.findById(existingGenreUUID)).thenReturn(Optional.of(existingGenre));

        GenreModel loadedGenre = genreService.loadGenre(existingGenreUUID);

        assertEquals(existingGenre, loadedGenre);
    }

    @Test
    void loadGenre_WhenGenreDoesNotExist_ShouldThrowNoExistDataException() {
        UUID nonExistentGenreUUID = UUID.randomUUID();

        when(genreRepositoryMock.findById(nonExistentGenreUUID)).thenReturn(Optional.empty());

        assertThrows(NoExistData.class, () -> genreService.loadGenre(nonExistentGenreUUID));
    }
}
