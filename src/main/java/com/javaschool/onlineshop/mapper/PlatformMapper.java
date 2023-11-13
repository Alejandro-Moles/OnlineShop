package com.javaschool.onlineshop.mapper;

import com.javaschool.onlineshop.dto.PlatformsRequestDTO;
import com.javaschool.onlineshop.models.PlatformsModel;
import org.springframework.stereotype.Service;

@Service
public class PlatformMapper {
    public PlatformsRequestDTO createPlatformDTO(PlatformsModel platforms){
        PlatformsRequestDTO platformsDTO = new PlatformsRequestDTO();
        platformsDTO.setUuid(platforms.getPlatformUuid());
        platformsDTO.setType(platforms.getType());
        platformsDTO.setIsDeleted(platforms.getIsDeleted());

        return platformsDTO;
    }
}
