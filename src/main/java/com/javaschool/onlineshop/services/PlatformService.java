package com.javaschool.onlineshop.services;

import com.javaschool.onlineshop.dto.PlatformsRequestDTO;
import com.javaschool.onlineshop.exception.ResourceDuplicate;
import com.javaschool.onlineshop.mapper.PlatformMapper;
import com.javaschool.onlineshop.models.Platforms;
import com.javaschool.onlineshop.repositories.PlatformsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class PlatformService {
    private final PlatformsRepository platformsRepository;
    private final PlatformMapper platformMapper;

    public PlatformsRequestDTO savePlatform(PlatformsRequestDTO platformsDTO) {
        Platforms platforms = createPlatformEntity(platformsDTO,  new Platforms());
        if (platformsRepository.existsByType(platforms.getType())) {
            throw new ResourceDuplicate("Platform already exists");
        }
        platformsRepository.save(platforms);
        return createPlatformsDTO(platforms);
    }

    private PlatformsRequestDTO createPlatformsDTO(Platforms platforms){
        return platformMapper.createPlatformDTO(platforms);
    }

    private Platforms createPlatformEntity(PlatformsRequestDTO platformsDTO, Platforms platforms){
        platforms.setType(platformsDTO.getType());
        platforms.setIsDeleted(false);
        return platforms;
    }
}
