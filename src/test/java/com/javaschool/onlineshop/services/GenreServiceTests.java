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

    /**
     * Test for saving a genre when the genre does not exist, checking if it successfully saves the genre.
     */
    @Test
    void saveGenre_WhenGenreDoesNotExist_ShouldSaveGenre() {
        // Mocking data
        when(genreMapperMock.createGenreDTO(any())).thenReturn(new GenreRequestDTO());
        when(genreRepositoryMock.existsByType(any())).thenReturn(false);

        // Creating a GenreDTO
        GenreRequestDTO genreDTO = new GenreRequestDTO();
        genreDTO.setType("New Genre");
        genreDTO.setUuid(UUID.randomUUID());
        genreDTO.setIsDeleted(false);

        // Calling the service method
        GenreRequestDTO savedGenreDTO = genreService.saveGenre(genreDTO);

        // Assertions
        assertNotNull(savedGenreDTO);
    }

    /**
     * Test for saving a genre when the genre exists, checking if it throws a ResourceDuplicate exception.
     */
    @Test
    void saveGenre_WhenGenreExists_ShouldThrowResourceDuplicateException() {
        // Mocking data
        when(genreRepositoryMock.existsByType(any())).thenReturn(true);

        // Creating a GenreDTO
        GenreRequestDTO genreDTO = new GenreRequestDTO();
        genreDTO.setType("Existing Genre");

        // Assertions
        assertThrows(ResourceDuplicate.class, () -> genreService.saveGenre(genreDTO));
    }

    /**
     * Test for updating a genre when the genre exists, checking if it successfully updates the genre.
     */
    @Test
    void updateGenre_WhenGenreExists_ShouldUpdateGenre() {
        // Mocking data
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

        // Calling the service method
        genreService.updateGenre(existingGenreUUID, updatedGenreDTO);

        // Verifying the save method was called
        verify(genreRepositoryMock).save(any(GenreModel.class));

        // Assertions
        assertEquals("Updated Genre", existingGenre.getType());
    }

    /**
     * Test for loading a genre when the genre exists, checking if it returns the loaded genre.
     */
    @Test
    void loadGenre_WhenGenreExists_ShouldReturnGenre() {
        // Mocking data
        UUID existingGenreUUID = UUID.randomUUID();
        GenreModel existingGenre = new GenreModel();
        existingGenre.setGenreUuid(existingGenreUUID);
        existingGenre.setType("Existing Genre");
        existingGenre.setIsDeleted(false);

        when(genreRepositoryMock.findById(existingGenreUUID)).thenReturn(Optional.of(existingGenre));

        // Calling the service method
        GenreModel loadedGenre = genreService.loadGenre(existingGenreUUID);

        // Assertions
        assertEquals(existingGenre, loadedGenre);
    }

    /**
     * Test for loading a genre when the genre does not exist, checking if it throws a NoExistData exception.
     */
    @Test
    void loadGenre_WhenGenreDoesNotExist_ShouldThrowNoExistDataException() {
        // Mocking data
        UUID nonExistentGenreUUID = UUID.randomUUID();
        when(genreRepositoryMock.findById(nonExistentGenreUUID)).thenReturn(Optional.empty());

        // Assertions
        assertThrows(NoExistData.class, () -> genreService.loadGenre(nonExistentGenreUUID));
    }

}
