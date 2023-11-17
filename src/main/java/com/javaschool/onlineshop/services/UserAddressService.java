package com.javaschool.onlineshop.services;

import com.javaschool.onlineshop.dto.ProductRequestDTO;
import com.javaschool.onlineshop.dto.UserAddressRequestDTO;
import com.javaschool.onlineshop.exception.NoExistData;
import com.javaschool.onlineshop.exception.ResourceDuplicate;
import com.javaschool.onlineshop.mapper.UserAddressMapper;
import com.javaschool.onlineshop.models.PostalCodeModel;
import com.javaschool.onlineshop.models.ProductsModel;
import com.javaschool.onlineshop.models.ShopUserModel;
import com.javaschool.onlineshop.models.UserAddressModel;
import com.javaschool.onlineshop.repositories.PostalCodeRepository;
import com.javaschool.onlineshop.repositories.ShopUserRepository;
import com.javaschool.onlineshop.repositories.UserAddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class UserAddressService {
    private final UserAddressRepository userAddressRepository;
    private final UserAddressMapper userAddressMapper;
    private final ShopUserRepository shopUserRepository;
    private final PostalCodeRepository postalCodeRepository;
    public UserAddressRequestDTO saveUserAddress(UserAddressRequestDTO userAddressDTO){
        UserAddressModel userAddress = createUserAddressEntity(userAddressDTO, new UserAddressModel());
        if(userAddressRepository.existsByApartamentAndStreetAndUserAndHome(userAddress.getApartament(), userAddress.getStreet(), userAddress.getUser(), userAddress.getHome())){
            throw new ResourceDuplicate("Address already exists within this credentials");
        }
        userAddressRepository.save(userAddress);
        return createUserAddressDTO(userAddress);
    }

    private UserAddressRequestDTO createUserAddressDTO(UserAddressModel userAddress){
        return userAddressMapper.createUserAddressDTO(userAddress);
    }

    @Transactional(readOnly = true)
    private ShopUserModel findShopUser(String mail){
        return shopUserRepository.findByMail(mail).orElseThrow(() -> new NoExistData("This shop user don't exist"));
    }

    @Transactional(readOnly = true)
    private PostalCodeModel findPostalCode(String content){
        return postalCodeRepository.findByContent(content).orElseThrow(null);
    }

    public UserAddressRequestDTO getUserAddressbyUuid(UUID uuid){
        UserAddressModel userModel = loadUserAddress(uuid);
        return createUserAddressDTO(userModel);
    }

    private UserAddressModel createUserAddressEntity(UserAddressRequestDTO userAddressDTO, UserAddressModel userAddress){
        userAddress.setApartament(userAddressDTO.getApartament());
        userAddress.setHome(userAddressDTO.getHome());
        userAddress.setStreet(userAddressDTO.getStreet());
        userAddress.setIsDeleted(userAddressDTO.getIsDeleted());
        userAddress.setUser(findShopUser(userAddressDTO.getUserMail()));
        userAddress.setPostal_code(findPostalCode(userAddressDTO.getPostalCode()));
        return userAddress;
    }

    @Transactional(readOnly = true)
    public List<UserAddressRequestDTO> getAllUserAddress(){
        return userAddressRepository.findAll().stream().map(this::createUserAddressDTO).toList();
    }

    @Transactional(readOnly = true)
    public List<UserAddressRequestDTO> getAllUserAddressForUser(UUID uuid){
        Optional<ShopUserModel> user  = shopUserRepository.findById(uuid);
        return userAddressRepository.findByUser(user).stream().map(this::createUserAddressDTO).toList();
    }

    @Transactional(readOnly = true)
    public UserAddressModel loadUserAddress (UUID uuid){
        return userAddressRepository.findById(uuid).orElseThrow(() -> new NoExistData("Product don't exist"));
    }
}
