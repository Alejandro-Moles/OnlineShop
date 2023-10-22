package com.javaschool.onlineshop.mapper;

import com.javaschool.onlineshop.dto.CountryRequestDTO;
import com.javaschool.onlineshop.models.Country;
import org.springframework.stereotype.Service;

@Service
public class CountyMapper {
    public CountryRequestDTO createCountryDTO(Country country){
        CountryRequestDTO countryDTO = new CountryRequestDTO();
        countryDTO.setUuid(country.getCountry_uuid());
        countryDTO.setName(country.getName());
        countryDTO.setDeleted(country.isDeleted());


        return countryDTO;
    }
}
