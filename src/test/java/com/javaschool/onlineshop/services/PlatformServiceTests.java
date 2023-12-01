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

    @Test
    void loadPlatform_ShouldReturnPlatformModel() {
        UUID platformUUID = UUID.randomUUID();

        when(platformsRepositoryMock.findById(platformUUID)).thenReturn(Optional.of(new PlatformsModel()));

        PlatformsModel loadedPlatform = assertDoesNotThrow(() -> platformService.loadPlatform(platformUUID));

        assertNotNull(loadedPlatform);
        verify(platformsRepositoryMock).findById(platformUUID);
    }

    @Test
    void loadPlatform_ShouldThrowException_PlatformNotFound() {
        UUID nonExistingPlatformUUID = UUID.randomUUID();

        when(platformsRepositoryMock.findById(nonExistingPlatformUUID)).thenReturn(Optional.empty());

        NoExistData exception = assertThrows(NoExistData.class, () ->
                platformService.loadPlatform(nonExistingPlatformUUID));

        assertEquals("Platform don't exist", exception.getMessage());

        verify(platformsRepositoryMock).findById(nonExistingPlatformUUID);
    }

    @Test
    void savePlatform_ShouldReturnPlatformDTO() {
        when(platformsRepositoryMock.existsByType(any())).thenReturn(false);

        when(platformMapperMock.createPlatformDTO(any())).thenReturn(new PlatformsRequestDTO());

        PlatformsRequestDTO savedPlatformDTO = assertDoesNotThrow(() -> platformService.savePlatform(new PlatformsRequestDTO()));

        assertNotNull(savedPlatformDTO);
        verify(platformsRepositoryMock).save(any(PlatformsModel.class));
    }

    @Test
    void savePlatform_ShouldThrowException_PlatformAlreadyExists() {
        when(platformsRepositoryMock.existsByType(any())).thenReturn(true);

        ResourceDuplicate exception = assertThrows(ResourceDuplicate.class, () ->
                platformService.savePlatform(new PlatformsRequestDTO()));

        assertEquals("Platform already exists", exception.getMessage());

        verify(platformsRepositoryMock, never()).save(any(PlatformsModel.class));
    }
}
