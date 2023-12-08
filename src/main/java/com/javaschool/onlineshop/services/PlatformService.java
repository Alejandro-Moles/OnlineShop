package com.javaschool.onlineshop.services;

import com.javaschool.onlineshop.dto.PlatformsRequestDTO;
import com.javaschool.onlineshop.exception.NoExistData;
import com.javaschool.onlineshop.exception.ResourceDuplicate;
import com.javaschool.onlineshop.mapper.PlatformMapper;
import com.javaschool.onlineshop.models.PlatformsModel;
import com.javaschool.onlineshop.repositories.PlatformsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class PlatformService {

    private final PlatformsRepository platformsRepository;
    private final PlatformMapper platformMapper;

    // Creates a PlatformsModel entity from a PlatformsRequestDTO
    private PlatformsModel createPlatformEntity(PlatformsRequestDTO platformsDTO, PlatformsModel platforms) {
        platforms.setType(platformsDTO.getType());
        platforms.setIsDeleted(platformsDTO.getIsDeleted());
        return platforms;
    }

    // Maps a PlatformsModel to a PlatformsRequestDTO
    private PlatformsRequestDTO createPlatformsDTO(PlatformsModel platforms) {
        return platformMapper.createPlatformDTO(platforms);
    }

    // Saves a PlatformsRequestDTO to the database
    @Transactional
    public PlatformsRequestDTO savePlatform(PlatformsRequestDTO platformsDTO) {
        PlatformsModel platforms = createPlatformEntity(platformsDTO,  new PlatformsModel());
        if (platformsRepository.existsByType(platforms.getType())) {
            throw new ResourceDuplicate("Platform already exists");
        }
        platformsRepository.save(platforms);
        return createPlatformsDTO(platforms);
    }

    // Updates a platform based on UUID
    @Transactional
    public void updatePlatform(UUID uuid, PlatformsRequestDTO platformsDTO) {
        PlatformsModel platforms = loadPlatform(uuid);
        if (platformsRepository.existsByType(platformsDTO.getType())) {
            throw new ResourceDuplicate("This platform already exists in the database");
        }
        createPlatformEntity(platformsDTO, platforms);
        platformsRepository.save(platforms);
    }

    // Retrieves all platforms from the database
    @Transactional(readOnly = true)
    public List<PlatformsRequestDTO> getAllPlatforms() {
        return platformsRepository.findAll().stream().map(this::createPlatformsDTO).toList();
    }

    // Retrieves a platform by UUID
    @Transactional(readOnly = true)
    public PlatformsModel loadPlatform(UUID uuid) {
        return platformsRepository.findById(uuid).orElseThrow(() -> new NoExistData("Platform doesn't exist"));
    }

    // Retrieves all available platforms from the database
    @Transactional(readOnly = true)
    public List<PlatformsRequestDTO> getAllAvailablePlatforms() {
        return platformsRepository.findAllByIsDeletedFalse().stream().map(this::createPlatformsDTO).toList();
    }
}
