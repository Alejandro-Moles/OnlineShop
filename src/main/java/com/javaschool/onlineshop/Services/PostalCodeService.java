package com.javaschool.onlineshop.Services;

import com.javaschool.onlineshop.DTO.PostalCodeRequestDTO;
import com.javaschool.onlineshop.Mapper.PostalCodeMapper;
import com.javaschool.onlineshop.Models.City;
import com.javaschool.onlineshop.Models.PostalCode;
import com.javaschool.onlineshop.Repositories.CityRepository;
import com.javaschool.onlineshop.Repositories.PostalCodeRepository;
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

        postalCode.setCity(loadCit(postalCodeDTO));

        postalCodeRepository.save(postalCode);
        return createPostalCodeDTO(postalCode);

    }

    private PostalCodeRequestDTO createPostalCodeDTO(PostalCode postalCode){
        return postalCodeMapper.createPostalCodeDTO(postalCode);
    }

    private City loadCit(PostalCodeRequestDTO postalCodeDTO){
       return cityRepository.findById(postalCodeRepository.findCiudadUuidByCiudadAndPais(postalCodeDTO.getCityName(), postalCodeDTO.getCountryName())).orElseThrow(null);
    }
}
