package com.javaschool.onlineshop.services;

import com.javaschool.onlineshop.dto.CityRequestDTO;
import com.javaschool.onlineshop.exception.NoExistData;
import com.javaschool.onlineshop.exception.ResourceDuplicate;
import com.javaschool.onlineshop.mapper.CityMapper;
import com.javaschool.onlineshop.models.CityModel;
import com.javaschool.onlineshop.models.CountryModel;
import com.javaschool.onlineshop.repositories.CityRepository;
import com.javaschool.onlineshop.repositories.CountryRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CityService {

    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;
    private final CityMapper cityMapper;

    private CityRequestDTO createCityDTO(CityModel city){
        return cityMapper.createCityDTO(city);
    }

    private CityModel createCityEntity(CityRequestDTO cityDTO, CityModel city){
        city.setName(cityDTO.getName());
        city.setDeleted(cityDTO.getIsDeleted());
        city.setCountry(findCountry(cityDTO.getCountryName()));
        return city;
    }

    @Transactional
    public CityRequestDTO saveCity(CityRequestDTO cityDTO){
        CityModel city = createCityEntity(cityDTO, new CityModel());
        CountryModel country = findCountry(cityDTO.getCountryName());

        if (cityRepository.existsByNameAndCountry(city.getName(), city.getCountry())) {
            throw new ResourceDuplicate("City already exists within country");
        }
        city.setCountry(country);
        cityRepository.save(city);
        return createCityDTO(city);
    }

    @Transactional(readOnly = true)
    private CountryModel findCountry(String name){
        return countryRepository.findByName(name).orElseThrow(() -> new NoExistData("This country don't exist"));
    }

    @Transactional
    public void updateCity(UUID uuid, CityRequestDTO cityDTO){
        CityModel city = loadCity(uuid);
        createCityEntity(cityDTO, city);
        cityRepository.save(city);
    }

    @Transactional(readOnly = true)
    public List<CityRequestDTO> getAllCities(){
        return cityRepository.findAll().stream().map(this::createCityDTO).toList();
    }

    @Transactional(readOnly = true)
    public CityModel loadCity(UUID uuid){
        return cityRepository.findById(uuid).orElseThrow(() -> new NoExistData("City don't exist"));
    }
}
