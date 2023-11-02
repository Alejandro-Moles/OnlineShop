package com.javaschool.onlineshop.services;

import com.javaschool.onlineshop.dto.PostalCodeRequestDTO;
import com.javaschool.onlineshop.exception.NoExistData;
import com.javaschool.onlineshop.exception.ResourceDuplicate;
import com.javaschool.onlineshop.mapper.PostalCodeMapper;
import com.javaschool.onlineshop.models.City;
import com.javaschool.onlineshop.models.PostalCode;
import com.javaschool.onlineshop.repositories.CityRepository;
import com.javaschool.onlineshop.repositories.PostalCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class PostalCodeService {
    private final PostalCodeRepository postalCodeRepository;
    private final PostalCodeMapper postalCodeMapper;
    private final CityRepository cityRepository;

    public PostalCodeRequestDTO savePostalCode(PostalCodeRequestDTO postalCodeDTO){
        PostalCode postalCode = createPostalCodeEntity(postalCodeDTO, new PostalCode());
        if (postalCodeRepository.existsByContent(postalCode.getContent())) {
            throw new ResourceDuplicate("Postal code already exists");
        }
        postalCodeRepository.save(postalCode);
        return createPostalCodeDTO(postalCode);
    }

    private PostalCodeRequestDTO createPostalCodeDTO(PostalCode postalCode){
        return postalCodeMapper.createPostalCodeDTO(postalCode);
    }

    @Transactional(readOnly = true)
    private City findCity(PostalCodeRequestDTO postalCodeDTO){
       return cityRepository.findById(cityRepository.findCityUuidByCityAndCountry(postalCodeDTO.getCityName(), postalCodeDTO.getCountryName())).orElseThrow(() -> new NoExistData("This city don't exist"));
    }

    private PostalCode createPostalCodeEntity(PostalCodeRequestDTO postalCodeDTO, PostalCode postalCode){
        postalCode.setContent(postalCodeDTO.getContent());
        postalCode.setDeleted(false);
        postalCode.setCity(findCity(postalCodeDTO));
        return postalCode;
    }

    @Transactional(readOnly = true)
    public List<PostalCodeRequestDTO> getAllPostalCodes(){
        return postalCodeRepository.findAll().stream().map(this::createPostalCodeDTO).toList();
    }
}
