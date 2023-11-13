package com.javaschool.onlineshop.services;

import com.javaschool.onlineshop.dto.StatusRequestDTO;
import com.javaschool.onlineshop.exception.ResourceDuplicate;
import com.javaschool.onlineshop.mapper.StatusMapper;
import com.javaschool.onlineshop.models.StatusModel;
import com.javaschool.onlineshop.repositories.StatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class StatusService {
    private final StatusRepository statusRepository;
    private final StatusMapper statusMapper;

    public StatusRequestDTO saveStatus(StatusRequestDTO statusDTO){
        StatusModel status = createStatusEntity(statusDTO, new StatusModel());
        if (statusRepository.existsByType(status.getType())) {
            throw new ResourceDuplicate("Status already exists");
        }
        statusRepository.save(status);
        return createStatusDTO(status);
    }

    private StatusRequestDTO createStatusDTO(StatusModel status){
        return statusMapper.createStatusDTO(status);
    }

    private StatusModel createStatusEntity(StatusRequestDTO statusDTO, StatusModel status){
        status.setType(statusDTO.getType());
        status.setIsDeleted(false);
        return status;
    }
}
