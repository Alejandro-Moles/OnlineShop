package com.javaschool.onlineshop.services;

import com.javaschool.onlineshop.dto.CityRequestDTO;
import com.javaschool.onlineshop.exception.NoExistData;
import com.javaschool.onlineshop.exception.ResourceDuplicate;
import com.javaschool.onlineshop.mapper.CityMapper;
import com.javaschool.onlineshop.models.CityModel;
import com.javaschool.onlineshop.models.CountryModel;
import com.javaschool.onlineshop.repositories.CityRepository;
import com.javaschool.onlineshop.repositories.CountryRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class CityServiceTests {

    private final CityRepository cityRepositoryMock = Mockito.mock(CityRepository.class);
    private final CountryRepository countryRepositoryMock = Mockito.mock(CountryRepository.class);
    private final CityMapper cityMapperMock = Mockito.mock(CityMapper.class);
    private final CityService cityService = new CityService(cityRepositoryMock, countryRepositoryMock, cityMapperMock);

    /**
     * Test for saving a city when the city does not exist, checking if it successfully saves the city.
     */
    @Test
    void saveCity_WhenCityDoesNotExist_ShouldSaveCity() {
        // Mocking data
        when(cityMapperMock.createCityDTO(any())).thenReturn(new CityRequestDTO());
        when(cityRepositoryMock.existsByNameAndCountry(any(), any())).thenReturn(false);
        when(countryRepositoryMock.findByName(any())).thenReturn(Optional.of(new CountryModel()));

        // Creating a CityDTO
        CityRequestDTO cityDTO = new CityRequestDTO();
        cityDTO.setName("New City");
        cityDTO.setCountryName("Country");
        cityDTO.setIsDeleted(false);

        // Calling the service method
        CityRequestDTO savedCityDTO = cityService.saveCity(cityDTO);

        // Assertions
        assertNotNull(savedCityDTO);
    }

    /**
     * Test for saving a city when the city exists, checking if it throws a ResourceDuplicate exception.
     */
    @Test
    void saveCity_WhenCityExists_ShouldThrowResourceDuplicateException() {
        // Mocking data
        when(countryRepositoryMock.findByName(any())).thenReturn(Optional.of(new CountryModel()));
        when(cityRepositoryMock.existsByNameAndCountry(any(), any())).thenReturn(true);

        // Creating a CityDTO
        CityRequestDTO cityDTO = new CityRequestDTO();
        cityDTO.setName("Existing City");
        cityDTO.setCountryName("Country");

        // Assertions
        assertThrows(ResourceDuplicate.class, () -> cityService.saveCity(cityDTO));
    }

    /**
     * Test for saving a city when the country does not exist, checking if it throws a NoExistData exception.
     */
    @Test
    void saveCity_WhenCountryDoesNotExist_ShouldThrowNoExistDataException() {
        // Mocking data
        when(countryRepositoryMock.findByName(any())).thenReturn(Optional.empty());

        // Creating a CityDTO
        CityRequestDTO cityDTO = new CityRequestDTO();
        cityDTO.setName("New City");
        cityDTO.setCountryName("NonExistentCountry");
        cityDTO.setIsDeleted(false);

        // Assertions
        assertThrows(NoExistData.class, () -> cityService.saveCity(cityDTO));
    }

    /**
     * Test for loading a city when the city exists, checking if it returns the loaded city.
     */
    @Test
    void loadCity_WhenCityExists_ShouldReturnCity() {
        // Mocking data
        UUID existingCityUUID = UUID.randomUUID();
        CityModel existingCity = new CityModel();
        existingCity.setCityUuid(existingCityUUID);
        existingCity.setName("Existing City");
        existingCity.setDeleted(false);
        existingCity.setCountry(new CountryModel());

        when(cityRepositoryMock.findById(existingCityUUID)).thenReturn(Optional.of(existingCity));

        // Calling the service method
        CityModel loadedCity = cityService.loadCity(existingCityUUID);

        // Assertions
        assertEquals(existingCity, loadedCity);
    }

    /**
     * Test for loading a city when the city does not exist, checking if it throws a NoExistData exception.
     */
    @Test
    void loadCity_WhenCityDoesNotExist_ShouldThrowNoExistDataException() {
        // Mocking data
        UUID nonExistentCityUUID = UUID.randomUUID();
        when(cityRepositoryMock.findById(nonExistentCityUUID)).thenReturn(Optional.empty());

        // Assertions
        assertThrows(NoExistData.class, () -> cityService.loadCity(nonExistentCityUUID));
    }

}
