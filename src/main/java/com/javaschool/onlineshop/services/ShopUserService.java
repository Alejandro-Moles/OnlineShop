package com.javaschool.onlineshop.services;

import com.javaschool.onlineshop.dto.ShopUserRequestDTO;
import com.javaschool.onlineshop.exception.NoExistData;
import com.javaschool.onlineshop.exception.ResourceDuplicate;
import com.javaschool.onlineshop.mapper.ShopUserMapper;
import com.javaschool.onlineshop.models.Role;
import com.javaschool.onlineshop.models.ShopUser;
import com.javaschool.onlineshop.repositories.RoleRepository;
import com.javaschool.onlineshop.repositories.ShopUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Base64;

@Service
@RequiredArgsConstructor
@Transactional
public class ShopUserService {
    private final ShopUserRepository shopUserRepository;
    private final ShopUserMapper shopUserMapper;
    private final RoleRepository roleRepository;

    public ShopUserRequestDTO saveShopUser(ShopUserRequestDTO shopUserDTO){
        ShopUser shopUser = createShopUserEntity(shopUserDTO, new ShopUser());
        if (shopUserRepository.existsByMail(shopUser.getMail())) {
            throw new ResourceDuplicate("Shop User already exists with that mail");
        }
        shopUserRepository.save(shopUser);
        return createShopUserDTO(shopUser);
    }

    @Transactional(readOnly = true)
    private Role findRole(String type){
        return roleRepository.findByType(type).orElseThrow(() -> new NoExistData("This rol don't exist"));
    }

    private ShopUserRequestDTO createShopUserDTO(ShopUser shopUser){
        return shopUserMapper.createShopUserDTO(shopUser);
    }

    //This method encrypts the password in the database so that it cannot be easily accessed.
    private String encodePasswordToBase64(String password){
        byte[] encodeBytes = Base64.getEncoder().encode(password.getBytes());
        return new String(encodeBytes);
    }

    //This method decrypts the encrypted password stored in the database.
    private String decodePasswordBase64(String encodedPassword){
        byte[] decodedBytes = Base64.getDecoder().decode(encodedPassword);
        return new String(decodedBytes);
    }

    private ShopUser createShopUserEntity(ShopUserRequestDTO shopUserDTO, ShopUser shopUser){
        shopUser.setName(shopUserDTO.getName());
        shopUser.setDeleted(false);
        shopUser.setUsers_rol(findRole(shopUserDTO.getUserRol()));
        shopUser.setMail(shopUserDTO.getMail());
        shopUser.setPassword(encodePasswordToBase64(shopUserDTO.getPassword()));
        shopUser.setDate(shopUserDTO.getBirth());
        shopUser.setSurname(shopUserDTO.getSurname());
        return shopUser;
    }

}
