package com.javaschool.onlineshop.services;

import com.javaschool.onlineshop.dto.CityRequestDTO;
import com.javaschool.onlineshop.exception.NoExistData;
import com.javaschool.onlineshop.exception.ResourceDuplicate;
import com.javaschool.onlineshop.mapper.CityMapper;
import com.javaschool.onlineshop.models.CityModel;
import com.javaschool.onlineshop.models.CountryModel;
import com.javaschool.onlineshop.repositories.CityRepository;
import com.javaschool.onlineshop.repositories.CountryRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CityService {

    // Injecting the CityRepository for database operations
    private final CityRepository cityRepository;

    // Injecting the CountryRepository for finding associated countries
    private final CountryRepository countryRepository;

    // Injecting the CityMapper for mapping between DTO and entity
    private final CityMapper cityMapper;

    // This method creates a CityDTO from a CityModel
    private CityRequestDTO createCityDTO(CityModel city) {
        return cityMapper.createCityDTO(city);
    }

    // This method updates a CityModel entity with data from a CityDTO
    private CityModel createCityEntity(CityRequestDTO cityDTO, CityModel city) {
        city.setName(cityDTO.getName());
        city.setDeleted(cityDTO.getIsDeleted());

        // Finding the associated country and setting it in the city entity
        city.setCountry(findCountry(cityDTO.getCountryName()));
        return city;
    }

    // This method saves a new city to the database
    @Transactional
    public CityRequestDTO saveCity(CityRequestDTO cityDTO) {
        // Creating a CityModel entity from the DTO
        CityModel city = createCityEntity(cityDTO, new CityModel());

        // Finding the associated country
        CountryModel country = findCountry(cityDTO.getCountryName());

        // Checking if a city with the same name already exists within the country
        if (cityRepository.existsByNameAndCountry(city.getName(), city.getCountry())) {
            throw new ResourceDuplicate("City already exists within country");
        }

        // Setting the country in the city entity and saving to the database
        city.setCountry(country);
        cityRepository.save(city);

        // Returning the saved city as a DTO
        return createCityDTO(city);
    }

    // This method finds a country by its name
    @Transactional(readOnly = true)
    private CountryModel findCountry(String name) {
        return countryRepository.findByName(name).orElseThrow(() -> new NoExistData("This country doesn't exist"));
    }

    // This method updates an existing city in the database
    @Transactional
    public void updateCity(UUID uuid, CityRequestDTO cityDTO) {
        // Loading the existing city from the database
        CityModel city = loadCity(uuid);

        // Updating the city entity with data from the DTO
        createCityEntity(cityDTO, city);

        // Saving the updated city to the database
        cityRepository.save(city);
    }

    // This method retrieves all cities from the database
    @Transactional(readOnly = true)
    public List<CityRequestDTO> getAllCities() {
        return cityRepository.findAll().stream().map(this::createCityDTO).toList();
    }

    // This method loads a city by its UUID from the database
    @Transactional(readOnly = true)
    public CityModel loadCity(UUID uuid) {
        return cityRepository.findById(uuid).orElseThrow(() -> new NoExistData("City doesn't exist"));
    }
}
