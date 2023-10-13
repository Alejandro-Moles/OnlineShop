package com.javaschool.onlineshop.Services;

import com.javaschool.onlineshop.DTO.StatusRequestDTO;
import com.javaschool.onlineshop.Mapper.StatusMapper;
import com.javaschool.onlineshop.Models.Status;
import com.javaschool.onlineshop.Repositories.StatusRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class StatusService {
    private final StatusRepository statusRepository;
    private final StatusMapper statusMapper;

    public StatusRequestDTO saveStatus(StatusRequestDTO statusDTO){
        Status status = new Status();
        status.setType(statusDTO.getType());
        status.setIsDeleted(false);

        statusRepository.save(status);
        return createStatusDTO(status);
    }

    private StatusRequestDTO createStatusDTO(Status status){
        return statusMapper.createStatusDTO(status);
    }
}
