package com.javaschool.onlineshop.Mapper;

import com.javaschool.onlineshop.DTO.PlatformsRequestDTO;
import com.javaschool.onlineshop.Models.Platforms;
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
