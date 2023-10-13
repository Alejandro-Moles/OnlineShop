package com.javaschool.onlineshop.Services;

import com.javaschool.onlineshop.DTO.CityRequestDTO;
import com.javaschool.onlineshop.Mapper.CityMapper;
import com.javaschool.onlineshop.Models.City;
import com.javaschool.onlineshop.Models.Country;
import com.javaschool.onlineshop.Repositories.CityRepository;
import com.javaschool.onlineshop.Repositories.CountryRepository;
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

        cityRepository.save(city);
        return createCityDTO(city);
    }

    private Country findCountry(String name){
        return countryRepository.findByName(name).orElseThrow(null);
    }

    private CityRequestDTO createCityDTO(City city){
        return cityMapper.createCityDTO(city);
    }
}
