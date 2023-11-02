package com.javaschool.onlineshop.services;

import com.javaschool.onlineshop.dto.CountryRequestDTO;
import com.javaschool.onlineshop.exception.ResourceDuplicate;
import com.javaschool.onlineshop.mapper.CountyMapper;
import com.javaschool.onlineshop.models.Country;
import com.javaschool.onlineshop.repositories.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CountryService {
    private final CountryRepository countryRepository;
    private final CountyMapper countyMapper;

    public CountryRequestDTO saveCountry(CountryRequestDTO countryDTO){
        Country country = createCountryEntity(countryDTO, new Country());
        if (countryRepository.existsByName(country.getName())) {
            throw new ResourceDuplicate("Country already exists");
        }
        countryRepository.save(country);
        return createCountryDTO(country);
    }

    private CountryRequestDTO createCountryDTO(Country country){
        return countyMapper.createCountryDTO(country);
    }

    private Country createCountryEntity(CountryRequestDTO countryDTO, Country country){
        country.setName(countryDTO.getName());
        country.setDeleted(false);
        return country;
    }

    @Transactional(readOnly = true)
    public List<CountryRequestDTO> getAllCountries(){
        return countryRepository.findAll().stream().map(this::createCountryDTO).toList();
    }
}
