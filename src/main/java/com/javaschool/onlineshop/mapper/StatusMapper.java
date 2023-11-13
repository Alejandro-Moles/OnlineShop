package com.javaschool.onlineshop.mapper;

import com.javaschool.onlineshop.dto.StatusRequestDTO;
import com.javaschool.onlineshop.models.StatusModel;
import org.springframework.stereotype.Service;

@Service
public class StatusMapper {
    public StatusRequestDTO createStatusDTO(StatusModel status){
        StatusRequestDTO statusDTO = new StatusRequestDTO();
        statusDTO.setUuid(status.getStatusUuid());
        statusDTO.setType(status.getType());
        statusDTO.setDeleted(status.getIsDeleted());

        return statusDTO;
    }
}
