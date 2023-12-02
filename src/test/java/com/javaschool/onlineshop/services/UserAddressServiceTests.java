package com.javaschool.onlineshop.services;

import com.javaschool.onlineshop.dto.UserAddressRequestDTO;
import com.javaschool.onlineshop.exception.NoExistData;
import com.javaschool.onlineshop.mapper.UserAddressMapper;
import com.javaschool.onlineshop.models.PostalCodeModel;
import com.javaschool.onlineshop.models.ShopUserModel;
import com.javaschool.onlineshop.models.UserAddressModel;
import com.javaschool.onlineshop.repositories.PostalCodeRepository;
import com.javaschool.onlineshop.repositories.ShopUserRepository;
import com.javaschool.onlineshop.repositories.UserAddressRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserAddressServiceTests {
    private final UserAddressRepository userAddressRepositoryMock = Mockito.mock(UserAddressRepository.class);
    private final UserAddressMapper userAddressMapperMock = Mockito.mock(UserAddressMapper.class);
    private final ShopUserRepository shopUserRepositoryMock = Mockito.mock(ShopUserRepository.class);
    private final PostalCodeRepository postalCodeRepositoryMock = Mockito.mock(PostalCodeRepository.class);
    private final UserAddressService userAddressService = new UserAddressService(
            userAddressRepositoryMock, userAddressMapperMock, shopUserRepositoryMock, postalCodeRepositoryMock);

    /**
     * Test for saving a new user address when the address does not exist, checking if it successfully saves the user address.
     */
    @Test
    void saveUserAddress_WhenAddressDoesNotExist_ShouldSaveUserAddress() {
        // Mocking data
        when(userAddressMapperMock.createUserAddressDTO(any())).thenReturn(new UserAddressRequestDTO());
        when(userAddressRepositoryMock.existsByApartamentAndStreetAndUserAndHome(any(), any(), any(), any())).thenReturn(false);
        when(shopUserRepositoryMock.findByMail(any())).thenReturn(Optional.of(new ShopUserModel()));
        when(postalCodeRepositoryMock.findByContent(any())).thenReturn(Optional.of(new PostalCodeModel()));

        // Creating a new user address DTO
        UserAddressRequestDTO userAddressDTO = new UserAddressRequestDTO();
        userAddressDTO.setApartament("123");
        userAddressDTO.setStreet("Main Street");
        userAddressDTO.setHome("456");
        userAddressDTO.setUserMail("test@example.com");
        userAddressDTO.setPostalCode("12345");

        // Calling the service method
        assertDoesNotThrow(() -> userAddressService.saveUserAddress(userAddressDTO));

        // Verifying that the repository method was called
        verify(userAddressRepositoryMock).save(any(UserAddressModel.class));
    }

    /**
     * Test for loading a user address when the address exists, checking if it returns the correct user address.
     */
    @Test
    void loadUserAddress_WhenAddressExists_ShouldReturnUserAddress() {
        // Mocking data
        UUID userAddressId = UUID.randomUUID();
        UserAddressModel userAddress = new UserAddressModel();
        userAddress.setAddressUuid(userAddressId);
        userAddress.setApartament("A1");
        userAddress.setStreet("Street1");
        userAddress.setHome("H1");

        // Mocking behavior of repositories
        when(userAddressRepositoryMock.findById(userAddressId)).thenReturn(Optional.of(userAddress));

        // Calling the service method
        UserAddressModel loadedUserAddress = userAddressService.loadUserAddress(userAddressId);

        // Assertions
        assertEquals(userAddress, loadedUserAddress);
    }

    /**
     * Test for loading a user address when the address does not exist, checking if it throws NoExistData exception.
     */
    @Test
    void loadUserAddress_WhenAddressDoesNotExist_ShouldThrowNoExistDataException() {
        // Mocking data
        UUID nonExistentUserAddressId = UUID.randomUUID();

        // Mocking behavior of repositories
        when(userAddressRepositoryMock.findById(nonExistentUserAddressId)).thenReturn(Optional.empty());

        // Assertions
        assertThrows(NoExistData.class, () -> userAddressService.loadUserAddress(nonExistentUserAddressId));
    }
}

