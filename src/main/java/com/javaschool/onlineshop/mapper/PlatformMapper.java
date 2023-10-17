package com.javaschool.onlineshop.mapper;

import com.javaschool.onlineshop.dto.PlatformsRequestDTO;
import com.javaschool.onlineshop.models.Platforms;
import org.springframework.stereotype.Service;

@Service
public class PlatformMapper {
    public PlatformsRequestDTO createPlatformDTO(Platforms platforms){
        PlatformsRequestDTO platformsDTO = new PlatformsRequestDTO();
        platformsDTO.setType(platforms.getType());
        platformsDTO.setDeleted(platforms.getIsDeleted());

        return platformsDTO;
    }
}
