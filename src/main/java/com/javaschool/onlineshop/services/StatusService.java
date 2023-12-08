package com.javaschool.onlineshop.services;

import com.javaschool.onlineshop.dto.StatusRequestDTO;
import com.javaschool.onlineshop.exception.ResourceDuplicate;
import com.javaschool.onlineshop.mapper.StatusMapper;
import com.javaschool.onlineshop.models.StatusModel;
import com.javaschool.onlineshop.repositories.StatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatusService {

    private final StatusRepository statusRepository;
    private final StatusMapper statusMapper;

    // Maps a StatusModel to a StatusRequestDTO
    private StatusRequestDTO createStatusDTO(StatusModel status) {
        return statusMapper.createStatusDTO(status);
    }

    // Creates a StatusModel entity from a StatusRequestDTO
    private StatusModel createStatusEntity(StatusRequestDTO statusDTO, StatusModel status) {
        status.setType(statusDTO.getType());
        status.setIsDeleted(false);
        return status;
    }

    // Saves a new Status to the database
    @Transactional
    public StatusRequestDTO saveStatus(StatusRequestDTO statusDTO) {
        StatusModel status = createStatusEntity(statusDTO, new StatusModel());
        if (statusRepository.existsByType(status.getType())) {
            throw new ResourceDuplicate("Status already exists");
        }
        statusRepository.save(status);
        return createStatusDTO(status);
    }

    // Retrieves all Status entries from the database
    @Transactional(readOnly = true)
    public List<StatusRequestDTO> getAllStatus() {
        return statusRepository.findAll().stream().map(this::createStatusDTO).toList();
    }
}
