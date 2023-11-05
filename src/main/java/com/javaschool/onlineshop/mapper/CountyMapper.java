package com.javaschool.onlineshop.mapper;

import com.javaschool.onlineshop.dto.CountryRequestDTO;
import com.javaschool.onlineshop.models.Country;
import org.springframework.stereotype.Service;

@Service
public class CountyMapper {
    public CountryRequestDTO createCountryDTO(Country country){
        CountryRequestDTO countryDTO = new CountryRequestDTO();
        countryDTO.setUuid(country.getCountryUuid());
        countryDTO.setName(country.getName());
        countryDTO.setIsDeleted(country.isDeleted());


        return countryDTO;
    }
}
