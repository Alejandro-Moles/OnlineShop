package com.javaschool.onlineshop.services;

import com.javaschool.onlineshop.dto.PostalCodeRequestDTO;
import com.javaschool.onlineshop.exception.NoExistData;
import com.javaschool.onlineshop.exception.ResourceDuplicate;
import com.javaschool.onlineshop.mapper.PostalCodeMapper;
import com.javaschool.onlineshop.models.City;
import com.javaschool.onlineshop.models.PostalCode;
import com.javaschool.onlineshop.repositories.CityRepository;
import com.javaschool.onlineshop.repositories.PostalCodeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Transactional
public class PostalCodeService {
    private final PostalCodeRepository postalCodeRepository;
    private final PostalCodeMapper postalCodeMapper;
    private final CityRepository cityRepository;

    public PostalCodeRequestDTO savePostalCode(PostalCodeRequestDTO postalCodeDTO){
        PostalCode postalCode = new PostalCode();
        postalCode.setContent(postalCodeDTO.getContent());
        postalCode.setDeleted(false);
        postalCode.setCity(findCity(postalCodeDTO));

        if (postalCodeRepository.existsByContent(postalCode.getContent())) {
            throw new ResourceDuplicate("Postal code already exists");
        }

        postalCodeRepository.save(postalCode);
        return createPostalCodeDTO(postalCode);

    }

    private PostalCodeRequestDTO createPostalCodeDTO(PostalCode postalCode){
        return postalCodeMapper.createPostalCodeDTO(postalCode);
    }

    private City findCity(PostalCodeRequestDTO postalCodeDTO){
       return cityRepository.findById(cityRepository.findCityUuidByCityAndCountry(postalCodeDTO.getCityName(), postalCodeDTO.getCountryName())).orElseThrow(() -> new NoExistData("This city don't exist"));
    }
}
