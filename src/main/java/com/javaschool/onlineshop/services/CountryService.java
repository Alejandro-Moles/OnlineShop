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

    private final CountryRepository countryRepository;
    private final CountyMapper countyMapper;

    private CountryRequestDTO createCountryDTO(CountryModel country){
        return countyMapper.createCountryDTO(country);
    }

    private CountryModel createCountryEntity(CountryRequestDTO countryDTO, CountryModel country){
        country.setName(countryDTO.getName());
        country.setDeleted(countryDTO.getIsDeleted());
        return country;
    }

    @Transactional(readOnly = true)
    public List<CountryRequestDTO> getAllCountries(){
        return countryRepository.findAll().stream().map(this::createCountryDTO).toList();
    }

    @Transactional(readOnly = true)
    public CountryModel loadCountry(UUID uuid){
        return countryRepository.findById(uuid).orElseThrow(() -> new NoExistData("Country don't exist"));
    }

    @Transactional
    public CountryRequestDTO saveCountry(CountryRequestDTO countryDTO){
        CountryModel country = createCountryEntity(countryDTO, new CountryModel());
        if (countryRepository.existsByName(country.getName())) {
            throw new ResourceDuplicate("Country already exists");
        }
        countryRepository.save(country);
        return createCountryDTO(country);
    }

    @Transactional
    public void updateCountry(UUID uuid, CountryRequestDTO countryDTO){
        CountryModel country = loadCountry(uuid);
        createCountryEntity(countryDTO, country);
        countryRepository.save(country);
    }
}

