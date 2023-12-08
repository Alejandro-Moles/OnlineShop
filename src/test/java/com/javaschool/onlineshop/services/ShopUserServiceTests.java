package com.javaschool.onlineshop.services;


import com.javaschool.onlineshop.dto.*;
import com.javaschool.onlineshop.exception.OldPasswordNotSame;
import com.javaschool.onlineshop.mapper.ShopUserMapper;
import com.javaschool.onlineshop.models.ShopUserModel;
import com.javaschool.onlineshop.repositories.RoleRepository;
import com.javaschool.onlineshop.repositories.ShopUserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ShopUserServiceTests {
    // Mocks for dependencies
    private final ShopUserRepository shopUserRepositoryMock = Mockito.mock(ShopUserRepository.class);
    private final ShopUserMapper shopUserMapperMock = Mockito.mock(ShopUserMapper.class);
    private final RoleRepository roleRepositoryMock = Mockito.mock(RoleRepository.class);
    private final PasswordEncoder passwordEncoderMock = Mockito.mock(PasswordEncoder.class);

    // Service instance with mocked dependencies
    private final ShopUserService shopUserService = new ShopUserService(
            shopUserRepositoryMock, shopUserMapperMock, roleRepositoryMock, passwordEncoderMock
    );

    /**
     * Test for the saveShopUser method, checking if it returns a ShopUserRequestDTO.
     */
    @Test
    void saveShopUser_ShouldReturnShopUserDTO() {
        // Mocking the behavior of shopUserMapperMock
        when(shopUserMapperMock.createShopUserDTO(any())).thenReturn(new ShopUserRequestDTO());

        // Mocking the behavior of shopUserRepositoryMock
        when(shopUserRepositoryMock.existsByMail(any())).thenReturn(false);

        // Creating a Date object for the birth date
        Date birthDate = java.sql.Date.valueOf(LocalDate.parse("1990-01-01"));

        // Creating a ShopUserRequestDTO object with sample data
        ShopUserRequestDTO shopUserDTO = new ShopUserRequestDTO();
        shopUserDTO.setName("John");
        shopUserDTO.setSurname("Doe");
        shopUserDTO.setMail("john.doe@example.com");
        shopUserDTO.setBirth(birthDate);
        shopUserDTO.setIsDeleted(false);

        // Calling the saveShopUser method of the shopUserService
        ShopUserRequestDTO savedShopUser = shopUserService.saveShopUser(shopUserDTO);

        // Assertions
        assertNotNull(savedShopUser);
    }

    /**
     * Test for the updateShopUser method, checking if it throws OldPasswordNotSame when the old password doesn't match.
     */
    @Test
    void updateShopUser_ShouldThrowException_OldPasswordNotSame() {
        // Mocking data
        when(shopUserRepositoryMock.findById(any())).thenReturn(Optional.of(new ShopUserModel()));
        when(passwordEncoderMock.matches(any(), any())).thenReturn(false);

        // Assertions
        OldPasswordNotSame exception = assertThrows(OldPasswordNotSame.class, () ->
                shopUserService.updateShopUserPassword(UUID.randomUUID(), new NewPasswordDTO()));

        assertEquals("The old password is not the same", exception.getMessage());
        verify(shopUserRepositoryMock, never()).save(any(ShopUserModel.class));  // Verifying that repository method was NOT called
    }

    /**
     * Test for the getUserStatistic method, checking if it returns a UserStatisticsDTO.
     */
    @Test
    void getUserStatistic_ShouldReturnUserStatisticsDTO() {
        // Mocking data
        when(shopUserRepositoryMock.findByMail(any())).thenReturn(Optional.of(new ShopUserModel()));
        when(shopUserRepositoryMock.getUserStatistics(any())).thenReturn(new UserStatisticsDTO());

        // Calling the service method
        UserStatisticsDTO userStatisticsDTO = assertDoesNotThrow(() ->
                shopUserService.getUserStatistic("user@mail.com"));

        // Assertions
        assertNotNull(userStatisticsDTO);
    }

}
