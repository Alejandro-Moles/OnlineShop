package com.javaschool.onlineshop.Services;

import com.javaschool.onlineshop.DTO.UserAddressRequestDTO;
import com.javaschool.onlineshop.Exception.ResourceDuplicate;
import com.javaschool.onlineshop.Mapper.UserAddressMapper;
import com.javaschool.onlineshop.Models.PostalCode;
import com.javaschool.onlineshop.Models.ShopUser;
import com.javaschool.onlineshop.Models.UserAddress;
import com.javaschool.onlineshop.Repositories.PostalCodeRepository;
import com.javaschool.onlineshop.Repositories.ShopUserRepository;
import com.javaschool.onlineshop.Repositories.UserAddressRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UserAddressService {
    private final UserAddressRepository userAddressRepository;
    private final UserAddressMapper userAddressMapper;
    private final ShopUserRepository shopUserRepository;
    private final PostalCodeRepository postalCodeRepository;
    public UserAddressRequestDTO saveUserAddress(UserAddressRequestDTO userAddressDTO){
        UserAddress userAddress = new UserAddress();

        userAddress.setApartament(userAddressDTO.getApartament());
        userAddress.setHome(userAddressDTO.getHome());
        userAddress.setStreet(userAddressDTO.getStreet());
        userAddress.setIsDeleted(userAddressDTO.getIsDeleted());
        userAddress.setUser(findShopUser(userAddressDTO.getUserMail()));
        userAddress.setPostal_code(findPostalCode(userAddressDTO.getPostalCode()));

        if(userAddressRepository.existsByApartamentAndStreetAndUserAndHome(userAddress.getApartament(), userAddress.getStreet(), userAddress.getUser(), userAddress.getHome())){
            throw new ResourceDuplicate("Address already exists within this credentials");
        }

        userAddressRepository.save(userAddress);
        return createUserAddressDTO(userAddress);
    }

    private UserAddressRequestDTO createUserAddressDTO(UserAddress userAddress){
        return userAddressMapper.createUserAddressDTO(userAddress);
    }

    private ShopUser findShopUser(String mail){
        return shopUserRepository.findByMail(mail).orElseThrow(null);
    }

    private PostalCode findPostalCode(String content){
        return postalCodeRepository.findByContent(content).orElseThrow(null);
    }
}
