package com.javaschool.onlineshop.services;

import com.javaschool.onlineshop.dto.PlatformsRequestDTO;
import com.javaschool.onlineshop.exception.ResourceDuplicate;
import com.javaschool.onlineshop.mapper.PlatformMapper;
import com.javaschool.onlineshop.models.Platforms;
import com.javaschool.onlineshop.repositories.PlatformsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Transactional
public class PlatformService {
    private final PlatformsRepository platformsRepository;
    private final PlatformMapper platformMapper;

    public PlatformsRequestDTO savePlatform(PlatformsRequestDTO platformsDTO) {
        Platforms platforms = new Platforms();
        platforms.setType(platformsDTO.getType());
        platforms.setIsDeleted(false);

        if (platformsRepository.existsByType(platforms.getType())) {
            throw new ResourceDuplicate("Platform already exists");
        }

        platformsRepository.save(platforms);
        return createPlatformsDTO(platforms);
    }

    private PlatformsRequestDTO createPlatformsDTO(Platforms platforms){
        return platformMapper.createPlatformDTO(platforms);
    }
}
