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

    @Test
    void saveCountry_WhenCountryDoesNotExist_ShouldSaveCountry() {
        when(countyMapperMock.createCountryDTO(any())).thenReturn(new CountryRequestDTO());
        when(countryRepositoryMock.existsByName(any())).thenReturn(false);

        CountryRequestDTO countryDTO = new CountryRequestDTO();
        countryDTO.setName("New Country");
        countryDTO.setUuid(UUID.randomUUID());
        countryDTO.setIsDeleted(false);
        CountryRequestDTO savedCountryDTO = countryService.saveCountry(countryDTO);

        assertNotNull(savedCountryDTO);
    }

    @Test
    void saveCountry_WhenCountryExists_ShouldThrowResourceDuplicateException() {
        when(countryRepositoryMock.existsByName(any())).thenReturn(true);

        CountryRequestDTO countryDTO = new CountryRequestDTO();
        countryDTO.setName("Existing Country");

        assertThrows(ResourceDuplicate.class, () -> countryService.saveCountry(countryDTO));
    }

    @Test
    void updateCountry_WhenCountryExists_ShouldUpdateCountry() {
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

        countryService.updateCountry(existingCountryUUID, updatedCountryDTO);

        verify(countryRepositoryMock).save(any(CountryModel.class));

        assertEquals("Updated Country", existingCountry.getName());
    }

    @Test
    void loadCountry_WhenCountryExists_ShouldReturnCountry() {
        UUID existingCountryUUID = UUID.randomUUID();

        CountryModel existingCountry = new CountryModel();
        existingCountry.setCountryUuid(existingCountryUUID);
        existingCountry.setName("Existing Country");
        existingCountry.setDeleted(false);

        when(countryRepositoryMock.findById(existingCountryUUID)).thenReturn(Optional.of(existingCountry));

        CountryModel loadedCountry = countryService.loadCountry(existingCountryUUID);

        assertEquals(existingCountry, loadedCountry);
    }

    @Test
    void loadCountry_WhenCountryDoesNotExist_ShouldThrowNoExistDataException() {
        UUID nonExistentCountryUUID = UUID.randomUUID();

        when(countryRepositoryMock.findById(nonExistentCountryUUID)).thenReturn(Optional.empty());

        assertThrows(NoExistData.class, () -> countryService.loadCountry(nonExistentCountryUUID));
    }
}
