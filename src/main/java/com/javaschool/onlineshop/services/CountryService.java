package com.javaschool.onlineshop.services;

import com.javaschool.onlineshop.dto.CountryRequestDTO;
import com.javaschool.onlineshop.exception.NoExistData;
import com.javaschool.onlineshop.exception.ResourceDuplicate;
import com.javaschool.onlineshop.mapper.CountyMapper;
import com.javaschool.onlineshop.models.CountryModel;
import com.javaschool.onlineshop.repositories.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CountryService {

    // Injecting the CountryRepository for database operations
    private final CountryRepository countryRepository;

    // Injecting the CountyMapper for mapping between DTO and entity
    private final CountyMapper countyMapper;

    // This method creates a CountryDTO from a CountryModel
    private CountryRequestDTO createCountryDTO(CountryModel country) {
        return countyMapper.createCountryDTO(country);
    }

    // This method updates a CountryModel entity with data from a CountryDTO
    private CountryModel createCountryEntity(CountryRequestDTO countryDTO, CountryModel country) {
        country.setName(countryDTO.getName());
        country.setDeleted(countryDTO.getIsDeleted());
        return country;
    }

    // This method retrieves all countries from the database
    @Transactional(readOnly = true)
    public List<CountryRequestDTO> getAllCountries() {
        return countryRepository.findAll().stream().map(this::createCountryDTO).toList();
    }

    // This method loads a country by its UUID from the database
    @Transactional(readOnly = true)
    public CountryModel loadCountry(UUID uuid) {
        return countryRepository.findById(uuid).orElseThrow(() -> new NoExistData("Country doesn't exist"));
    }

    // This method saves a new country to the database
    @Transactional
    public CountryRequestDTO saveCountry(CountryRequestDTO countryDTO) {
        // Creating a CountryModel entity from the DTO
        CountryModel country = createCountryEntity(countryDTO, new CountryModel());

        // Checking if a country with the same name already exists
        if (countryRepository.existsByName(country.getName())) {
            throw new ResourceDuplicate("Country already exists");
        }

        // Saving the country to the database
        countryRepository.save(country);

        // Returning the saved country as a DTO
        return createCountryDTO(country);
    }

    // This method updates an existing country in the database
    @Transactional
    public void updateCountry(UUID uuid, CountryRequestDTO countryDTO) {
        // Loading the existing country from the database
        CountryModel country = loadCountry(uuid);

        // Updating the country entity with data from the DTO
        createCountryEntity(countryDTO, country);

        // Saving the updated country to the database
        countryRepository.save(country);
    }
}

