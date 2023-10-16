package com.javaschool.onlineshop.Services;

import com.javaschool.onlineshop.DTO.ShopUserRequestDTO;
import com.javaschool.onlineshop.Mapper.ShopUserMapper;
import com.javaschool.onlineshop.Models.Role;
import com.javaschool.onlineshop.Models.ShopUser;
import com.javaschool.onlineshop.Repositories.RoleRepository;
import com.javaschool.onlineshop.Repositories.ShopUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
@RequiredArgsConstructor
@Transactional
public class ShopUserService {
    private final ShopUserRepository shopUserRepository;
    private final ShopUserMapper shopUserMapper;
    private final RoleRepository roleRepository;

    public ShopUserRequestDTO saveShopUser(ShopUserRequestDTO shopUserDTO){
        ShopUser shopUser = new ShopUser();

        shopUser.setName(shopUserDTO.getName());
        shopUser.setDeleted(false);
        shopUser.setUsers_rol(findRole(shopUserDTO.getUserRol()));
        shopUser.setMail(shopUserDTO.getMail());
        shopUser.setPassword(encodePasswordToBase64(shopUserDTO.getPassword()));
        shopUser.setDate(shopUserDTO.getBirth());
        shopUser.setSurname(shopUserDTO.getSurname());

        shopUserRepository.save(shopUser);
        return createShopUserDTO(shopUser);
    }

    private Role findRole(String type){
        return roleRepository.findByType(type).orElseThrow(null);
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

}
