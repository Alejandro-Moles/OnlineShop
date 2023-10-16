package com.javaschool.onlineshop.Services;

import com.javaschool.onlineshop.DTO.PlatformsRequestDTO;
import com.javaschool.onlineshop.Mapper.PlatformMapper;
import com.javaschool.onlineshop.Models.Platforms;
import com.javaschool.onlineshop.Repositories.PlatformsRepository;
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

        platformsRepository.save(platforms);
        return createPlatformsDTO(platforms);
    }

    private PlatformsRequestDTO createPlatformsDTO(Platforms platforms){
        return platformMapper.createPlatformDTO(platforms);
    }
}
