package com.javaschool.onlineshop.services;

import com.javaschool.onlineshop.dto.CityRequestDTO;
import com.javaschool.onlineshop.exception.NoExistData;
import com.javaschool.onlineshop.exception.ResourceDuplicate;
import com.javaschool.onlineshop.mapper.CityMapper;
import com.javaschool.onlineshop.models.City;
import com.javaschool.onlineshop.models.Country;
import com.javaschool.onlineshop.repositories.CityRepository;
import com.javaschool.onlineshop.repositories.CountryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class CityService {
    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;
    private final CityMapper cityMapper;

    public CityRequestDTO saveCity(CityRequestDTO cityDTO){
        City city = new City();
        city.setName(cityDTO.getName());
        city.setDeleted(false);
        city.setCountry(findCountry(cityDTO.getCountryName()));

        if (cityRepository.existsByNameAndCountry(city.getName(), city.getCountry())) {
            throw new ResourceDuplicate("City already exists within country");
        }

        cityRepository.save(city);
        return createCityDTO(city);
    }

    private Country findCountry(String name){
        return countryRepository.findByName(name).orElseThrow(() -> new NoExistData("This country don't exist"));
    }

    private CityRequestDTO createCityDTO(City city){
        return cityMapper.createCityDTO(city);
    }
}
