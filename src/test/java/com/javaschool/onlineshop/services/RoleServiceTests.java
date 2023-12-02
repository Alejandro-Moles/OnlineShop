package com.javaschool.onlineshop.services;
import com.javaschool.onlineshop.dto.RoleRequestDTO;
import com.javaschool.onlineshop.exception.ResourceDuplicate;
import com.javaschool.onlineshop.mapper.RoleMapper;
import com.javaschool.onlineshop.models.RoleModel;
import com.javaschool.onlineshop.repositories.RoleRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class RoleServiceTests {
    // Mocks for dependencies
    private final RoleRepository roleRepositoryMock = Mockito.mock(RoleRepository.class);

    private final RoleMapper roleMapperMock = Mockito.mock(RoleMapper.class);

    // Service instance with mocked dependencies
    private final RoleService roleService = new RoleService(roleRepositoryMock, roleMapperMock);

    /**
     * Test for the getAllRoles method, checking if it returns a non-empty list of RoleRequestDTO.
     */
    @Test
    void getAllRoles_ShouldReturnListOfRoleDTOs() {
        // Mocking data
        when(roleRepositoryMock.findAll()).thenReturn(List.of(new RoleModel()));

        // Calling the service method
        List<RoleRequestDTO> allRoles = roleService.getAllRoles();

        // Assertions
        assertNotNull(allRoles);
        assertFalse(allRoles.isEmpty());
        verify(roleRepositoryMock).findAll();  // Verifying that repository method was called
    }

    /**
     * Test for the saveRole method, ensuring it returns a RoleRequestDTO when the repository doesn't have a role with the same type.
     */
    @Test
    void saveRole_ShouldReturnRoleDTO() {
        // Mocking data
        when(roleRepositoryMock.existsByType(any())).thenReturn(false);
        when(roleMapperMock.createRoleDTO(any())).thenReturn(new RoleRequestDTO());

        // Calling the service method
        RoleRequestDTO savedRoleDTO = assertDoesNotThrow(() -> roleService.saveRole(new RoleRequestDTO()));

        // Assertions
        assertNotNull(savedRoleDTO);
        verify(roleRepositoryMock).save(any(RoleModel.class));  // Verifying that repository method was called
    }

    /**
     * Test for the saveRole method, checking if it throws ResourceDuplicate when attempting to save a role that already exists.
     */
    @Test
    void saveRole_ShouldThrowException_RoleAlreadyExists() {
        // Mocking data
        when(roleRepositoryMock.existsByType(any())).thenReturn(true);

        // Assertions
        ResourceDuplicate exception = assertThrows(ResourceDuplicate.class, () ->
                roleService.saveRole(new RoleRequestDTO()));

        assertEquals("Role already exists", exception.getMessage());
        verify(roleRepositoryMock, never()).save(any(RoleModel.class));  // Verifying that repository method was NOT called
    }
}
