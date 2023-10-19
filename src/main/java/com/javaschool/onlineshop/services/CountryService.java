package com.javaschool.onlineshop.services;

import com.javaschool.onlineshop.dto.CountryRequestDTO;
import com.javaschool.onlineshop.exception.ResourceDuplicate;
import com.javaschool.onlineshop.mapper.CountyMapper;
import com.javaschool.onlineshop.models.Country;
import com.javaschool.onlineshop.repositories.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CountryService {
    private final CountryRepository countryRepository;
    private final CountyMapper countyMapper;

    public CountryRequestDTO saveCountry(CountryRequestDTO countryDTO){
        Country country = new Country();
        country.setName(countryDTO.getName());
        country.setDeleted(false);

        if (countryRepository.existsByName(country.getName())) {
            throw new ResourceDuplicate("Country already exists");
        }

        countryRepository.save(country);
        return createCountyDTO(country);
    }

    private CountryRequestDTO createCountyDTO(Country country){
        return countyMapper.createCountryDTO(country);
    }
}
