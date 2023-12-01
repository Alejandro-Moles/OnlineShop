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

    @Test
    void saveCity_WhenCityDoesNotExist_ShouldSaveCity() {
        when(cityMapperMock.createCityDTO(any())).thenReturn(new CityRequestDTO());
        when(cityRepositoryMock.existsByNameAndCountry(any(), any())).thenReturn(false);
        when(countryRepositoryMock.findByName(any())).thenReturn(Optional.of(new CountryModel()));

        CityRequestDTO cityDTO = new CityRequestDTO();
        cityDTO.setName("New City");
        cityDTO.setCountryName("Country");
        cityDTO.setIsDeleted(false);
        CityRequestDTO savedCityDTO = cityService.saveCity(cityDTO);

        assertNotNull(savedCityDTO);
    }

    @Test
    void saveCity_WhenCityExists_ShouldThrowResourceDuplicateException() {
        when(countryRepositoryMock.findByName(any())).thenReturn(Optional.of(new CountryModel()));

        when(cityRepositoryMock.existsByNameAndCountry(any(), any())).thenReturn(true);

        CityRequestDTO cityDTO = new CityRequestDTO();
        cityDTO.setName("Existing City");
        cityDTO.setCountryName("Country");

        assertThrows(ResourceDuplicate.class, () -> cityService.saveCity(cityDTO));
    }

    @Test
    void saveCity_WhenCountryDoesNotExist_ShouldThrowNoExistDataException() {
        when(countryRepositoryMock.findByName(any())).thenReturn(Optional.empty());

        CityRequestDTO cityDTO = new CityRequestDTO();
        cityDTO.setName("New City");
        cityDTO.setCountryName("NonExistentCountry");
        cityDTO.setIsDeleted(false);

        assertThrows(NoExistData.class, () -> cityService.saveCity(cityDTO));
    }

    @Test
    void loadCity_WhenCityExists_ShouldReturnCity() {
        UUID existingCityUUID = UUID.randomUUID();

        CityModel existingCity = new CityModel();
        existingCity.setCityUuid(existingCityUUID);
        existingCity.setName("Existing City");
        existingCity.setDeleted(false);
        existingCity.setCountry(new CountryModel());

        when(cityRepositoryMock.findById(existingCityUUID)).thenReturn(Optional.of(existingCity));

        CityModel loadedCity = cityService.loadCity(existingCityUUID);

        assertEquals(existingCity, loadedCity);
    }

    @Test
    void loadCity_WhenCityDoesNotExist_ShouldThrowNoExistDataException() {
        UUID nonExistentCityUUID = UUID.randomUUID();

        when(cityRepositoryMock.findById(nonExistentCityUUID)).thenReturn(Optional.empty());

        assertThrows(NoExistData.class, () -> cityService.loadCity(nonExistentCityUUID));
    }
}
