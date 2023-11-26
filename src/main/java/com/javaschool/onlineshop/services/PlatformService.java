package com.javaschool.onlineshop.services;

import com.javaschool.onlineshop.dto.CategoryRequestDTO;
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
@Transactional
public class PlatformService {
    private final PlatformsRepository platformsRepository;
    private final PlatformMapper platformMapper;

    public PlatformsRequestDTO savePlatform(PlatformsRequestDTO platformsDTO) {
        PlatformsModel platforms = createPlatformEntity(platformsDTO,  new PlatformsModel());
        if (platformsRepository.existsByType(platforms.getType())) {
            throw new ResourceDuplicate("Platform already exists");
        }
        platformsRepository.save(platforms);
        return createPlatformsDTO(platforms);
    }

    private PlatformsRequestDTO createPlatformsDTO(PlatformsModel platforms){
        return platformMapper.createPlatformDTO(platforms);
    }

    public void updatePlatform(UUID uuid, PlatformsRequestDTO platformsDTO){
        PlatformsModel platforms = loadPlatform(uuid);
        createPlatformEntity(platformsDTO, platforms);
        platformsRepository.save(platforms);
    }

    private PlatformsModel createPlatformEntity(PlatformsRequestDTO platformsDTO, PlatformsModel platforms){
        platforms.setType(platformsDTO.getType());
        platforms.setIsDeleted(platformsDTO.getIsDeleted());
        return platforms;
    }

    @Transactional(readOnly = true)
    public List<PlatformsRequestDTO> getAllPlatforms(){
        return platformsRepository.findAll().stream().map(this::createPlatformsDTO).toList();
    }

    @Transactional(readOnly = true)
    private PlatformsModel loadPlatform(UUID uuid){
        return platformsRepository.findById(uuid).orElseThrow(() -> new NoExistData("Platform don't exist"));
    }

    @Transactional(readOnly = true)
    public List<PlatformsRequestDTO> getAllAvailablePlatforms(){
        return platformsRepository.findAllByIsDeletedFalse().stream().map(this::createPlatformsDTO).toList();
    }
}
