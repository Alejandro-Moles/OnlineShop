package com.javaschool.onlineshop.Services;

import com.javaschool.onlineshop.DTO.CountryRequestDTO;
import com.javaschool.onlineshop.Mapper.CountyMapper;
import com.javaschool.onlineshop.Models.Country;
import com.javaschool.onlineshop.Repositories.CountryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class CountryService {
    private final CountryRepository countryRepository;
    private final CountyMapper countyMapper;

    public CountryRequestDTO saveContry(CountryRequestDTO countryDTO){
        Country country = new Country();
        country.setName(countryDTO.getName());
        country.setDeleted(false);

        countryRepository.save(country);
        return createCountyDTO(country);
    }

    private CountryRequestDTO createCountyDTO(Country country){
        return countyMapper.createCountryDTO(country);
    }
}
