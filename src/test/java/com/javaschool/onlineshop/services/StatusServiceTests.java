package com.javaschool.onlineshop.services;


import com.javaschool.onlineshop.dto.StatusRequestDTO;
import com.javaschool.onlineshop.exception.ResourceDuplicate;
import com.javaschool.onlineshop.mapper.StatusMapper;
import com.javaschool.onlineshop.models.StatusModel;
import com.javaschool.onlineshop.repositories.StatusRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class StatusServiceTests {
    private final StatusRepository statusRepositoryMock = Mockito.mock(StatusRepository.class);
    private final StatusMapper statusMapperMock = Mockito.mock(StatusMapper.class);
    private final StatusService statusService = new StatusService(statusRepositoryMock, statusMapperMock);

    /**
     * Test for saving a new status when the status does not exist, checking if it successfully saves the status.
     */
    @Test
    void saveStatus_WhenStatusDoesNotExist_ShouldSaveStatus() {
        // Mocking data
        when(statusMapperMock.createStatusDTO(any())).thenReturn(new StatusRequestDTO());
        when(statusRepositoryMock.existsByType(any())).thenReturn(false);

        // Creating a new status DTO
        StatusRequestDTO statusDTO = new StatusRequestDTO();
        statusDTO.setType("New Status");

        // Calling the service method
        assertDoesNotThrow(() -> statusService.saveStatus(statusDTO));

        // Verifying that the repository method was called
        verify(statusRepositoryMock).save(any(StatusModel.class));
    }

    /**
     * Test for saving a new status when the status already exists, checking if it throws ResourceDuplicate exception.
     */
    @Test
    void saveStatus_WhenStatusExists_ShouldThrowResourceDuplicateException() {
        // Mocking data
        when(statusRepositoryMock.existsByType(any())).thenReturn(true);

        // Creating a new status DTO
        StatusRequestDTO statusDTO = new StatusRequestDTO();
        statusDTO.setType("Existing Status");

        // Assertions
        assertThrows(ResourceDuplicate.class, () -> statusService.saveStatus(statusDTO));
    }

    /**
     * Test for retrieving all statuses, checking if it returns a list of StatusDTOs.
     */
    @Test
    void getAllStatus_ShouldReturnListOfStatusDTOs() {
        // Mocking data
        StatusModel status1 = new StatusModel();
        status1.setStatusUuid(UUID.randomUUID());
        status1.setType("Status1");

        StatusModel status2 = new StatusModel();
        status2.setStatusUuid(UUID.randomUUID());
        status2.setType("Status2");

        List<StatusModel> statusList = Arrays.asList(status1, status2);

        // Mocking behavior of repositories
        when(statusRepositoryMock.findAll()).thenReturn(statusList);
        when(statusMapperMock.createStatusDTO(status1)).thenReturn(new StatusRequestDTO(status1.getStatusUuid(), status1.getType()));
        when(statusMapperMock.createStatusDTO(status2)).thenReturn(new StatusRequestDTO(status2.getStatusUuid(), status2.getType()));

        // Calling the service method
        List<StatusRequestDTO> allStatus = statusService.getAllStatus();

        // Assertions
        assertEquals(2, allStatus.size());
    }
}
