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

    @Test
    void savePostalCode_WhenPostalCodeDoesNotExist_ShouldSavePostalCodeAndReturnDTO() {
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

        PostalCodeRequestDTO savedPostalCodeDTO = postalCodeService.savePostalCode(postalCodeDTO);

        assertNotNull(savedPostalCodeDTO);
        verify(postalCodeRepositoryMock).save(any(PostalCodeModel.class));
    }

    @Test
    void savePostalCode_WhenPostalCodeExists_ShouldThrowResourceDuplicateException() {
        when(countryRepositoryMock.findByName(any())).thenReturn(Optional.of(new CountryModel()));
        when(cityRepositoryMock.findByName(any())).thenReturn(Optional.of(new CityModel()));
        when(postalCodeRepositoryMock.existsByContent(any())).thenReturn(true);
        when(cityRepositoryMock.findById(any())).thenReturn(Optional.of(new CityModel()));

        PostalCodeRequestDTO postalCodeDTO = new PostalCodeRequestDTO();
        postalCodeDTO.setContent("Existing PostalCode");
        postalCodeDTO.setCityName("City");
        postalCodeDTO.setCountryName("Country");

        assertThrows(ResourceDuplicate.class, () -> postalCodeService.savePostalCode(postalCodeDTO));
    }

    @Test
    void updatePostalCode_WhenPostalCodeExists_ShouldUpdatePostalCode() {
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

        postalCodeService.updatePostalCode(postalCodeUUID, postalCodeDTO);

        verify(postalCodeRepositoryMock).save(postalCodeModel);
    }

    @Test
    void updatePostalCode_WhenPostalCodeDoesNotExist_ShouldThrowNoExistDataException() {
        UUID nonExistentPostalCodeUUID = UUID.randomUUID();
        PostalCodeRequestDTO postalCodeDTO = new PostalCodeRequestDTO();
        postalCodeDTO.setContent("12345");
        postalCodeDTO.setIsDeleted(false);
        postalCodeDTO.setCityName("CityName");
        postalCodeDTO.setCountryName("CountryName");

        when(postalCodeRepositoryMock.findById(nonExistentPostalCodeUUID)).thenReturn(Optional.empty());

        NoExistData exception = assertThrows(NoExistData.class, () -> postalCodeService.updatePostalCode(nonExistentPostalCodeUUID, postalCodeDTO));

        assertEquals("Postal code don't exist", exception.getMessage());

        verify(postalCodeRepositoryMock, never()).save(any(PostalCodeModel.class));
    }

    @Test
    void loadPostalCode_WhenPostalCodeExists_ShouldReturnPostalCodeModel() {
        UUID postalCodeUUID = UUID.randomUUID();
        PostalCodeModel postalCodeModel = new PostalCodeModel();
        postalCodeModel.setPostalCodeUuid(postalCodeUUID);

        when(postalCodeRepositoryMock.findById(postalCodeUUID)).thenReturn(Optional.of(postalCodeModel));

        PostalCodeModel foundPostalCode = assertDoesNotThrow(() -> postalCodeService.loadPostalCode(postalCodeUUID));

        assertNotNull(foundPostalCode);
        verify(postalCodeRepositoryMock).findById(postalCodeUUID);
    }

    @Test
    void loadPostalCode_WhenPostalCodeDoesNotExist_ShouldThrowNoExistDataException() {
        UUID nonExistentPostalCodeUUID = UUID.randomUUID();

        when(postalCodeRepositoryMock.findById(nonExistentPostalCodeUUID)).thenReturn(Optional.empty());

        NoExistData exception = assertThrows(NoExistData.class, () -> postalCodeService.loadPostalCode(nonExistentPostalCodeUUID));

        assertEquals("Postal code don't exist", exception.getMessage());

        verify(postalCodeRepositoryMock).findById(nonExistentPostalCodeUUID);
    }
}
