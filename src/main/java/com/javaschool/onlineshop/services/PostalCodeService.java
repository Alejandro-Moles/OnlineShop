package com.javaschool.onlineshop.services;

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
public class PostalCodeService {

    private final PostalCodeRepository postalCodeRepository;
    private final PostalCodeMapper postalCodeMapper;
    private final CityRepository cityRepository;

    // Maps a PostalCodeModel to a PostalCodeRequestDTO
    private PostalCodeRequestDTO createPostalCodeDTO(PostalCodeModel postalCode) {
        return postalCodeMapper.createPostalCodeDTO(postalCode);
    }

    // Creates a PostalCodeModel entity from a PostalCodeRequestDTO
    private PostalCodeModel createPostalCodeEntity(PostalCodeRequestDTO postalCodeDTO, PostalCodeModel postalCode) {
        postalCode.setContent(postalCodeDTO.getContent());
        postalCode.setDeleted(postalCodeDTO.getIsDeleted());
        postalCode.setCity(findCity(postalCodeDTO));
        return postalCode;
    }

    // Saves a PostalCodeRequestDTO to the database
    @Transactional
    public PostalCodeRequestDTO savePostalCode(PostalCodeRequestDTO postalCodeDTO) {
        PostalCodeModel postalCode = createPostalCodeEntity(postalCodeDTO, new PostalCodeModel());
        if (postalCodeRepository.existsByContent(postalCode.getContent())) {
            throw new ResourceDuplicate("Postal code already exists");
        }
        postalCodeRepository.save(postalCode);
        return createPostalCodeDTO(postalCode);
    }

    // Finds a CityModel based on a PostalCodeRequestDTO
    @Transactional(readOnly = true)
    CityModel findCity(PostalCodeRequestDTO postalCodeDTO) {
        return cityRepository.findById(cityRepository.findCityUuidByCityAndCountry(postalCodeDTO.getCityName(), postalCodeDTO.getCountryName()))
                .orElseThrow(() -> new NoExistData("This city doesn't exist"));
    }

    // Updates a postal code based on UUID
    @Transactional
    public void updatePostalCode(UUID uuid, PostalCodeRequestDTO postalCodeDTO) {
        PostalCodeModel postalCode = loadPostalCode(uuid);
        createPostalCodeEntity(postalCodeDTO, postalCode);
        postalCodeRepository.save(postalCode);
    }

    // Retrieves all postal codes from the database
    @Transactional(readOnly = true)
    public List<PostalCodeRequestDTO> getAllPostalCodes() {
        return postalCodeRepository.findAll().stream().map(this::createPostalCodeDTO).toList();
    }

    // Retrieves a postal code by UUID
    @Transactional(readOnly = true)
    public PostalCodeModel loadPostalCode(UUID uuid) {
        return postalCodeRepository.findById(uuid).orElseThrow(() -> new NoExistData("Postal code doesn't exist"));
    }

    // Retrieves all available postal codes from the database
    @Transactional(readOnly = true)
    public List<PostalCodeRequestDTO> getAllAvailablePostalCodes() {
        return postalCodeRepository.findAllByIsDeletedFalse().stream().map(this::createPostalCodeDTO).toList();
    }
}
