package com.javaschool.onlineshop.Mapper;

import com.javaschool.onlineshop.DTO.PostalCodeRequestDTO;
import com.javaschool.onlineshop.Models.PostalCode;
import org.springframework.stereotype.Service;

@Service
public class PostalCodeMapper {
    public PostalCodeRequestDTO createPostalCodeDTO(PostalCode postalCode){
        PostalCodeRequestDTO postalDTO = new PostalCodeRequestDTO();
        postalDTO.setContent(postalCode.getContent());
        postalDTO.setDeleted(postalCode.isDeleted());
        postalDTO.setCityName(postalCode.getCity().getName());
        postalDTO.setCountryName(postalCode.getCity().getCountry().getName());

        return postalDTO;
    }
}
