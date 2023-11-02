package com.javaschool.onlineshop.services;

import com.javaschool.onlineshop.dto.CityRequestDTO;
import com.javaschool.onlineshop.exception.NoExistData;
import com.javaschool.onlineshop.exception.ResourceDuplicate;
import com.javaschool.onlineshop.mapper.CityMapper;
import com.javaschool.onlineshop.models.City;
import com.javaschool.onlineshop.models.Country;
import com.javaschool.onlineshop.repositories.CityRepository;
import com.javaschool.onlineshop.repositories.CountryRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CityService {
    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;
    private final CityMapper cityMapper;

    public CityRequestDTO saveCity(CityRequestDTO cityDTO){
        City city = createCityEntity(cityDTO, new City());
        if (cityRepository.existsByNameAndCountry(city.getName(), city.getCountry())) {
            throw new ResourceDuplicate("City already exists within country");
        }

        cityRepository.save(city);
        return createCityDTO(city);
    }

    @Transactional(readOnly = true)
    private Country findCountry(String name){
        return countryRepository.findByName(name).orElseThrow(() -> new NoExistData("This country don't exist"));
    }

    private CityRequestDTO createCityDTO(City city){
        return cityMapper.createCityDTO(city);
    }

    private City createCityEntity(CityRequestDTO cityDTO, City city){
        city.setName(cityDTO.getName());
        city.setDeleted(cityDTO.isDeleted());
        city.setCountry(findCountry(cityDTO.getCountryName()));
        return city;
    }

    @Transactional(readOnly = true)
    public List<CityRequestDTO> getAllCities(){
        return cityRepository.findAll().stream().map(this::createCityDTO).toList();
    }
}
