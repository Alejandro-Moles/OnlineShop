package com.javaschool.onlineshop.Mapper;

import com.javaschool.onlineshop.DTO.CountryRequestDTO;
import com.javaschool.onlineshop.Models.Country;
import org.springframework.stereotype.Service;

@Service
public class CountyMapper {
    public CountryRequestDTO createCountryDTO(Country country){
        CountryRequestDTO countryDTO = new CountryRequestDTO();
        countryDTO.setName(country.getName());
        countryDTO.setDeleted(country.isDeleted());


        return countryDTO;
    }
}
