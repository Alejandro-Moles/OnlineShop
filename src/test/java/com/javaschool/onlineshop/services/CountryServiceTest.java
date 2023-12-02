package com.javaschool.onlineshop.services;

import com.javaschool.onlineshop.dto.CountryRequestDTO;
import com.javaschool.onlineshop.exception.NoExistData;
import com.javaschool.onlineshop.exception.ResourceDuplicate;
import com.javaschool.onlineshop.mapper.CountyMapper;
import com.javaschool.onlineshop.models.CountryModel;
import com.javaschool.onlineshop.repositories.CountryRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CountryServiceTest {
    private final CountryRepository countryRepositoryMock = Mockito.mock(CountryRepository.class);
    private final CountyMapper countyMapperMock = Mockito.mock(CountyMapper.class);
    private final CountryService countryService = new CountryService(countryRepositoryMock, countyMapperMock);

    /**
     * Test for saving a country when the country does not exist, checking if it successfully saves the country.
     */
    @Test
    void saveCountry_WhenCountryDoesNotExist_ShouldSaveCountry() {
        // Mocking data
        when(countyMapperMock.createCountryDTO(any())).thenReturn(new CountryRequestDTO());
        when(countryRepositoryMock.existsByName(any())).thenReturn(false);

        // Creating a CountryDTO
        CountryRequestDTO countryDTO = new CountryRequestDTO();
        countryDTO.setName("New Country");
        countryDTO.setUuid(UUID.randomUUID());
        countryDTO.setIsDeleted(false);

        // Calling the service method
        CountryRequestDTO savedCountryDTO = countryService.saveCountry(countryDTO);

        // Assertions
        assertNotNull(savedCountryDTO);
    }

    /**
     * Test for saving a country when the country exists, checking if it throws a ResourceDuplicate exception.
     */
    @Test
    void saveCountry_WhenCountryExists_ShouldThrowResourceDuplicateException() {
        // Mocking data
        when(countryRepositoryMock.existsByName(any())).thenReturn(true);

        // Creating a CountryDTO
        CountryRequestDTO countryDTO = new CountryRequestDTO();
        countryDTO.setName("Existing Country");

        // Assertions
        assertThrows(ResourceDuplicate.class, () -> countryService.saveCountry(countryDTO));
    }

    /**
     * Test for updating a country when the country exists, checking if it successfully updates the country.
     */
    @Test
    void updateCountry_WhenCountryExists_ShouldUpdateCountry() {
        // Mocking data
        UUID existingCountryUUID = UUID.randomUUID();
        CountryRequestDTO updatedCountryDTO = new CountryRequestDTO();
        updatedCountryDTO.setName("Updated Country");
        updatedCountryDTO.setUuid(existingCountryUUID);
        updatedCountryDTO.setIsDeleted(false);

        CountryModel existingCountry = new CountryModel();
        existingCountry.setCountryUuid(existingCountryUUID);
        existingCountry.setName("Existing Country");
        existingCountry.setDeleted(false);

        when(countryRepositoryMock.findById(existingCountryUUID)).thenReturn(Optional.of(existingCountry));

        // Calling the service method
        countryService.updateCountry(existingCountryUUID, updatedCountryDTO);

        // Verifying the save method was called
        verify(countryRepositoryMock).save(any(CountryModel.class));

        // Assertions
        assertEquals("Updated Country", existingCountry.getName());
    }

    /**
     * Test for loading a country when the country exists, checking if it returns the loaded country.
     */
    @Test
    void loadCountry_WhenCountryExists_ShouldReturnCountry() {
        // Mocking data
        UUID existingCountryUUID = UUID.randomUUID();
        CountryModel existingCountry = new CountryModel();
        existingCountry.setCountryUuid(existingCountryUUID);
        existingCountry.setName("Existing Country");
        existingCountry.setDeleted(false);

        when(countryRepositoryMock.findById(existingCountryUUID)).thenReturn(Optional.of(existingCountry));

        // Calling the service method
        CountryModel loadedCountry = countryService.loadCountry(existingCountryUUID);

        // Assertions
        assertEquals(existingCountry, loadedCountry);
    }

    /**
     * Test for loading a country when the country does not exist, checking if it throws a NoExistData exception.
     */
    @Test
    void loadCountry_WhenCountryDoesNotExist_ShouldThrowNoExistDataException() {
        // Mocking data
        UUID nonExistentCountryUUID = UUID.randomUUID();
        when(countryRepositoryMock.findById(nonExistentCountryUUID)).thenReturn(Optional.empty());

        // Assertions
        assertThrows(NoExistData.class, () -> countryService.loadCountry(nonExistentCountryUUID));
    }

}
