package com.javaschool.onlineshop.mapper;

import com.javaschool.onlineshop.dto.UserAddressRequestDTO;
import com.javaschool.onlineshop.models.UserAddress;
import org.springframework.stereotype.Service;

@Service
public class UserAddressMapper {
    public UserAddressRequestDTO createUserAddressDTO(UserAddress userAddress){
        UserAddressRequestDTO userAddressDTO = new UserAddressRequestDTO();
        userAddressDTO.setUuid(userAddress.getAddressUuid());
        userAddressDTO.setApartament(userAddress.getApartament());
        userAddressDTO.setHome(userAddress.getHome());
        userAddressDTO.setStreet(userAddress.getStreet());
        userAddressDTO.setIsDeleted(userAddress.getIsDeleted());
        userAddressDTO.setPostalCode(userAddress.getPostal_code().getContent());
        userAddressDTO.setUserMail(userAddress.getUser().getMail());

        return userAddressDTO;
    }
}
