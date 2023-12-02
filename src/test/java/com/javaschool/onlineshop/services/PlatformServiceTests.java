package com.javaschool.onlineshop.services;

import com.javaschool.onlineshop.dto.PlatformsRequestDTO;
import com.javaschool.onlineshop.exception.NoExistData;
import com.javaschool.onlineshop.exception.ResourceDuplicate;
import com.javaschool.onlineshop.mapper.PlatformMapper;
import com.javaschool.onlineshop.models.PlatformsModel;
import com.javaschool.onlineshop.repositories.PlatformsRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PlatformServiceTests {
    private final PlatformsRepository platformsRepositoryMock = Mockito.mock(PlatformsRepository.class);
    private final PlatformMapper platformMapperMock = Mockito.mock(PlatformMapper.class);
    private final PlatformService platformService = new PlatformService(platformsRepositoryMock, platformMapperMock);

    /**
     * Test for loading a platform when the platform exists, checking if it returns the correct PlatformsModel.
     */
    @Test
    void loadPlatform_ShouldReturnPlatformModel() {
        // Mocking data
        UUID platformUUID = UUID.randomUUID();
        when(platformsRepositoryMock.findById(platformUUID)).thenReturn(Optional.of(new PlatformsModel()));

        // Calling the service method
        PlatformsModel loadedPlatform = assertDoesNotThrow(() -> platformService.loadPlatform(platformUUID));

        // Assertions
        assertNotNull(loadedPlatform);
        verify(platformsRepositoryMock).findById(platformUUID);
    }

    /**
     * Test for loading a platform when the platform does not exist, checking if it throws a NoExistData exception.
     */
    @Test
    void loadPlatform_ShouldThrowException_PlatformNotFound() {
        // Mocking data
        UUID nonExistingPlatformUUID = UUID.randomUUID();
        when(platformsRepositoryMock.findById(nonExistingPlatformUUID)).thenReturn(Optional.empty());

        // Assertions
        NoExistData exception = assertThrows(NoExistData.class, () -> platformService.loadPlatform(nonExistingPlatformUUID));

        assertEquals("Platform don't exist", exception.getMessage());
        verify(platformsRepositoryMock).findById(nonExistingPlatformUUID);
    }

    /**
     * Test for saving a platform when the platform does not already exist, checking if it successfully saves the platform and returns a DTO.
     */
    @Test
    void savePlatform_ShouldReturnPlatformDTO() {
        // Mocking data
        when(platformsRepositoryMock.existsByType(any())).thenReturn(false);
        when(platformMapperMock.createPlatformDTO(any())).thenReturn(new PlatformsRequestDTO());

        // Calling the service method
        PlatformsRequestDTO savedPlatformDTO = assertDoesNotThrow(() -> platformService.savePlatform(new PlatformsRequestDTO()));

        // Assertions
        assertNotNull(savedPlatformDTO);
        verify(platformsRepositoryMock).save(any(PlatformsModel.class));
    }

    /**
     * Test for saving a platform when the platform already exists, checking if it throws a ResourceDuplicate exception.
     */
    @Test
    void savePlatform_ShouldThrowException_PlatformAlreadyExists() {
        // Mocking data
        when(platformsRepositoryMock.existsByType(any())).thenReturn(true);

        // Assertions
        ResourceDuplicate exception = assertThrows(ResourceDuplicate.class, () ->
                platformService.savePlatform(new PlatformsRequestDTO()));

        assertEquals("Platform already exists", exception.getMessage());
        verify(platformsRepositoryMock, never()).save(any(PlatformsModel.class));
    }
}
