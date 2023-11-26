package com.javaschool.onlineshop.services;

import com.javaschool.onlineshop.dto.PlatformsRequestDTO;
import com.javaschool.onlineshop.dto.PostalCodeRequestDTO;
import com.javaschool.onlineshop.exception.NoExistData;
import com.javaschool.onlineshop.exception.ResourceDuplicate;
import com.javaschool.onlineshop.mapper.PostalCodeMapper;
import com.javaschool.onlineshop.models.CityModel;
import com.javaschool.onlineshop.models.PostalCodeModel;
import com.javaschool.onlineshop.repositories.CityRepository;
import com.javaschool.onlineshop.repositories.PostalCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Transactional
public class PostalCodeService {
    private final PostalCodeRepository postalCodeRepository;
    private final PostalCodeMapper postalCodeMapper;
    private final CityRepository cityRepository;

    public PostalCodeRequestDTO savePostalCode(PostalCodeRequestDTO postalCodeDTO){
        PostalCodeModel postalCode = createPostalCodeEntity(postalCodeDTO, new PostalCodeModel());
        if (postalCodeRepository.existsByContent(postalCode.getContent())) {
            throw new ResourceDuplicate("Postal code already exists");
        }
        postalCodeRepository.save(postalCode);
        return createPostalCodeDTO(postalCode);
    }

    private PostalCodeRequestDTO createPostalCodeDTO(PostalCodeModel postalCode){
        return postalCodeMapper.createPostalCodeDTO(postalCode);
    }

    @Transactional(readOnly = true)
    private CityModel findCity(PostalCodeRequestDTO postalCodeDTO){
       return cityRepository.findById(cityRepository.findCityUuidByCityAndCountry(postalCodeDTO.getCityName(), postalCodeDTO.getCountryName())).orElseThrow(() -> new NoExistData("This city don't exist"));
    }

    public void updatePostalCode(UUID uuid, PostalCodeRequestDTO postalCodeDTO){
        PostalCodeModel postalCode = loadPostalCode(uuid);
        createPostalCodeEntity(postalCodeDTO, postalCode);
        postalCodeRepository.save(postalCode);
    }

    private PostalCodeModel createPostalCodeEntity(PostalCodeRequestDTO postalCodeDTO, PostalCodeModel postalCode){
        postalCode.setContent(postalCodeDTO.getContent());
        postalCode.setDeleted(postalCodeDTO.getIsDeleted());
        postalCode.setCity(findCity(postalCodeDTO));
        return postalCode;
    }

    @Transactional(readOnly = true)
    public List<PostalCodeRequestDTO> getAllPostalCodes(){
        return postalCodeRepository.findAll().stream().map(this::createPostalCodeDTO).toList();
    }

    @Transactional(readOnly = true)
    private PostalCodeModel loadPostalCode(UUID uuid){
        return postalCodeRepository.findById(uuid).orElseThrow(() -> new NoExistData("Postal code don't exist"));
    }

    @Transactional(readOnly = true)
    public List<PostalCodeRequestDTO> getAllAvailablePostalCodes(){
        return postalCodeRepository.findAllByIsDeletedFalse().stream().map(this::createPostalCodeDTO).toList();
    }
}
