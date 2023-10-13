package com.javaschool.onlineshop.Mapper;

import com.javaschool.onlineshop.DTO.StatusRequestDTO;
import com.javaschool.onlineshop.Models.Status;
import org.springframework.stereotype.Service;

@Service
public class StatusMapper {
    public StatusRequestDTO createStatusDTO(Status status){
        StatusRequestDTO statusDTO = new StatusRequestDTO();
        statusDTO.setType(status.getType());
        statusDTO.setDeleted(status.getIsDeleted());

        return statusDTO;
    }
}
