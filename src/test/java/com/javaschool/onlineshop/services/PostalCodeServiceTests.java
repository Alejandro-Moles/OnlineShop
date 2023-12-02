package com.javaschool.onlineshop.services;

import com.javaschool.onlineshop.dto.CityRequestDTO;
import com.javaschool.onlineshop.dto.OrderRequestDTO;
import com.javaschool.onlineshop.dto.PostalCodeRequestDTO;
import com.javaschool.onlineshop.exception.NoExistData;
import com.javaschool.onlineshop.exception.ResourceDuplicate;
import com.javaschool.onlineshop.mapper.PostalCodeMapper;
import com.javaschool.onlineshop.models.CityModel;
import com.javaschool.onlineshop.models.CountryModel;
import com.javaschool.onlineshop.models.PostalCodeModel;
import com.javaschool.onlineshop.repositories.CityRepository;
import com.javaschool.onlineshop.repositories.CountryRepository;
import com.javaschool.onlineshop.repositories.PostalCodeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PostalCodeServiceTests {
    private final PostalCodeRepository postalCodeRepositoryMock = Mockito.mock(PostalCodeRepository.class);
    private final PostalCodeMapper postalCodeMapperMock = Mockito.mock(PostalCodeMapper.class);
    private final CityRepository cityRepositoryMock = Mockito.mock(CityRepository.class);
    private final CountryRepository countryRepositoryMock = Mockito.mock(CountryRepository.class);
    private final PostalCodeService postalCodeService = new PostalCodeService(
            postalCodeRepositoryMock, postalCodeMapperMock, cityRepositoryMock
    );

    /**
     * Test for saving a new postal code when the postal code does not exist, checking if it successfully saves the postal code and returns a DTO.
     */
    @Test
    void savePostalCode_WhenPostalCodeDoesNotExist_ShouldSavePostalCodeAndReturnDTO() {
        // Mocking data
        PostalCodeRequestDTO postalCodeDTO = new PostalCodeRequestDTO();
        postalCodeDTO.setContent("12345");
        postalCodeDTO.setIsDeleted(false);
        postalCodeDTO.setCityName("CityName");
        postalCodeDTO.setCountryName("CountryName");

        PostalCodeModel postalCodeModel = new PostalCodeModel();
        postalCodeModel.setContent(postalCodeDTO.getContent());
        postalCodeModel.setDeleted(postalCodeDTO.getIsDeleted());
        CityModel cityModel = new CityModel();
        when(cityRepositoryMock.findById(any())).thenReturn(Optional.of(cityModel));
        when(postalCodeMapperMock.createPostalCodeDTO(any())).thenReturn(new PostalCodeRequestDTO());
        when(postalCodeRepositoryMock.existsByContent(postalCodeDTO.getContent())).thenReturn(false);

        // Calling the service method
        PostalCodeRequestDTO savedPostalCodeDTO = postalCodeService.savePostalCode(postalCodeDTO);

        // Assertions
        assertNotNull(savedPostalCodeDTO);
        verify(postalCodeRepositoryMock).save(any(PostalCodeModel.class));
    }

    /**
     * Test for saving a postal code when the postal code already exists, checking if it throws a ResourceDuplicate exception.
     */
    @Test
    void savePostalCode_WhenPostalCodeExists_ShouldThrowResourceDuplicateException() {
        // Mocking data
        when(countryRepositoryMock.findByName(any())).thenReturn(Optional.of(new CountryModel()));
        when(cityRepositoryMock.findByName(any())).thenReturn(Optional.of(new CityModel()));
        when(postalCodeRepositoryMock.existsByContent(any())).thenReturn(true);
        when(cityRepositoryMock.findById(any())).thenReturn(Optional.of(new CityModel()));

        PostalCodeRequestDTO postalCodeDTO = new PostalCodeRequestDTO();
        postalCodeDTO.setContent("Existing PostalCode");
        postalCodeDTO.setCityName("City");
        postalCodeDTO.setCountryName("Country");

        // Assertions
        assertThrows(ResourceDuplicate.class, () -> postalCodeService.savePostalCode(postalCodeDTO));
    }

    /**
     * Test for updating a postal code when the postal code exists, checking if it successfully updates the postal code.
     */
    @Test
    void updatePostalCode_WhenPostalCodeExists_ShouldUpdatePostalCode() {
        // Mocking data
        UUID postalCodeUUID = UUID.randomUUID();
        PostalCodeRequestDTO postalCodeDTO = new PostalCodeRequestDTO();
        postalCodeDTO.setContent("12345");
        postalCodeDTO.setIsDeleted(false);
        postalCodeDTO.setCityName("CityName");
        postalCodeDTO.setCountryName("CountryName");

        PostalCodeModel postalCodeModel = new PostalCodeModel();
        postalCodeModel.setPostalCodeUuid(postalCodeUUID);
        when(postalCodeRepositoryMock.findById(postalCodeUUID)).thenReturn(Optional.of(postalCodeModel));
        CityModel cityModel = new CityModel();
        when(cityRepositoryMock.findById(any())).thenReturn(Optional.of(cityModel));

        // Calling the service method
        postalCodeService.updatePostalCode(postalCodeUUID, postalCodeDTO);

        // Verifying that the repository method was called
        verify(postalCodeRepositoryMock).save(postalCodeModel);
    }

    /**
     * Test for updating a postal code when the postal code does not exist, checking if it throws a NoExistData exception.
     */
    @Test
    void updatePostalCode_WhenPostalCodeDoesNotExist_ShouldThrowNoExistDataException() {
        // Mocking data
        UUID nonExistentPostalCodeUUID = UUID.randomUUID();
        PostalCodeRequestDTO postalCodeDTO = new PostalCodeRequestDTO();
        postalCodeDTO.setContent("12345");
        postalCodeDTO.setIsDeleted(false);
        postalCodeDTO.setCityName("CityName");
        postalCodeDTO.setCountryName("CountryName");

        when(postalCodeRepositoryMock.findById(nonExistentPostalCodeUUID)).thenReturn(Optional.empty());

        // Assertions
        NoExistData exception = assertThrows(NoExistData.class, () -> postalCodeService.updatePostalCode(nonExistentPostalCodeUUID, postalCodeDTO));

        assertEquals("Postal code don't exist", exception.getMessage());

        // Verifying that the repository method was NOT called
        verify(postalCodeRepositoryMock, never()).save(any(PostalCodeModel.class));
    }

    /**
     * Test for loading a postal code when the postal code exists, checking if it returns the correct PostalCodeModel.
     */
    @Test
    void loadPostalCode_WhenPostalCodeExists_ShouldReturnPostalCodeModel() {
        // Mocking data
        UUID postalCodeUUID = UUID.randomUUID();
        PostalCodeModel postalCodeModel = new PostalCodeModel();
        postalCodeModel.setPostalCodeUuid(postalCodeUUID);

        when(postalCodeRepositoryMock.findById(postalCodeUUID)).thenReturn(Optional.of(postalCodeModel));

        // Calling the service method
        PostalCodeModel foundPostalCode = assertDoesNotThrow(() -> postalCodeService.loadPostalCode(postalCodeUUID));

        // Assertions
        assertNotNull(foundPostalCode);
        verify(postalCodeRepositoryMock).findById(postalCodeUUID);
    }

    /**
     * Test for loading a postal code when the postal code does not exist, checking if it throws a NoExistData exception.
     */
    @Test
    void loadPostalCode_WhenPostalCodeDoesNotExist_ShouldThrowNoExistDataException() {
        // Mocking data
        UUID nonExistentPostalCodeUUID = UUID.randomUUID();

        when(postalCodeRepositoryMock.findById(nonExistentPostalCodeUUID)).thenReturn(Optional.empty());

        // Assertions
        NoExistData exception = assertThrows(NoExistData.class, () -> postalCodeService.loadPostalCode(nonExistentPostalCodeUUID));

        assertEquals("Postal code don't exist", exception.getMessage());

        // Verifying that the repository method was called
        verify(postalCodeRepositoryMock).findById(nonExistentPostalCodeUUID);
    }
}
