package com.javaschool.onlineshop.services;

import com.javaschool.onlineshop.dto.UserAddressRequestDTO;
import com.javaschool.onlineshop.exception.NoExistData;
import com.javaschool.onlineshop.exception.ResourceDuplicate;
import com.javaschool.onlineshop.mapper.UserAddressMapper;
import com.javaschool.onlineshop.models.PostalCode;
import com.javaschool.onlineshop.models.ShopUser;
import com.javaschool.onlineshop.models.UserAddress;
import com.javaschool.onlineshop.repositories.PostalCodeRepository;
import com.javaschool.onlineshop.repositories.ShopUserRepository;
import com.javaschool.onlineshop.repositories.UserAddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserAddressService {
    private final UserAddressRepository userAddressRepository;
    private final UserAddressMapper userAddressMapper;
    private final ShopUserRepository shopUserRepository;
    private final PostalCodeRepository postalCodeRepository;
    public UserAddressRequestDTO saveUserAddress(UserAddressRequestDTO userAddressDTO){
        UserAddress userAddress = createUserAddressEntity(userAddressDTO, new UserAddress());
        if(userAddressRepository.existsByApartamentAndStreetAndUserAndHome(userAddress.getApartament(), userAddress.getStreet(), userAddress.getUser(), userAddress.getHome())){
            throw new ResourceDuplicate("Address already exists within this credentials");
        }
        userAddressRepository.save(userAddress);
        return createUserAddressDTO(userAddress);
    }

    private UserAddressRequestDTO createUserAddressDTO(UserAddress userAddress){
        return userAddressMapper.createUserAddressDTO(userAddress);
    }

    @Transactional(readOnly = true)
    private ShopUser findShopUser(String mail){
        return shopUserRepository.findByMail(mail).orElseThrow(() -> new NoExistData("This shop user don't exist"));
    }

    @Transactional(readOnly = true)
    private PostalCode findPostalCode(String content){
        return postalCodeRepository.findByContent(content).orElseThrow(null);
    }

    private UserAddress createUserAddressEntity(UserAddressRequestDTO userAddressDTO, UserAddress userAddress){
        userAddress.setApartament(userAddressDTO.getApartament());
        userAddress.setHome(userAddressDTO.getHome());
        userAddress.setStreet(userAddressDTO.getStreet());
        userAddress.setIsDeleted(userAddressDTO.getIsDeleted());
        userAddress.setUser(findShopUser(userAddressDTO.getUserMail()));
        userAddress.setPostal_code(findPostalCode(userAddressDTO.getPostalCode()));
        return userAddress;
    }
}
