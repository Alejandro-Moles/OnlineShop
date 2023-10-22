package com.javaschool.onlineshop.mapper;

import com.javaschool.onlineshop.dto.StatusRequestDTO;
import com.javaschool.onlineshop.models.Status;
import org.springframework.stereotype.Service;

@Service
public class StatusMapper {
    public StatusRequestDTO createStatusDTO(Status status){
        StatusRequestDTO statusDTO = new StatusRequestDTO();
        statusDTO.setUuid(status.getStatus_uuid());
        statusDTO.setType(status.getType());
        statusDTO.setDeleted(status.getIsDeleted());

        return statusDTO;
    }
}
