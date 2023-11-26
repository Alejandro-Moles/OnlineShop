package com.javaschool.onlineshop.mapper;

import com.javaschool.onlineshop.dto.RegisterRequestDTO;
import com.javaschool.onlineshop.dto.ShopUserRequestDTO;
import com.javaschool.onlineshop.models.RoleModel;
import com.javaschool.onlineshop.models.ShopUserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShopUserMapper {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ShopUserMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public ShopUserRequestDTO createShopUserDTO(ShopUserModel shopUser){
        ShopUserRequestDTO shopUserDTO = new ShopUserRequestDTO();
        shopUserDTO.setUuid(shopUser.getUserUuid());
        shopUserDTO.setName(shopUser.getName());
        shopUserDTO.setSurname(shopUser.getSurname());
        shopUserDTO.setMail(shopUser.getMail());
        shopUserDTO.setPassword(shopUser.getPassword());
        shopUserDTO.setBirth(shopUser.getDate());
        shopUserDTO.setIsDeleted(shopUser.isDeleted());

        List<String> roleNames = shopUser.getRoles().stream()
                .map(RoleModel::getType)
                .collect(Collectors.toList());
        shopUserDTO.setRoles(roleNames);
        return shopUserDTO;
    }


    public ShopUserModel createShopUserEntity(RegisterRequestDTO registerDTO, ShopUserModel shopUser){
        shopUser.setMail(registerDTO.getMail());
        shopUser.setName(registerDTO.getName());
        shopUser.setSurname(registerDTO.getSurname());
        shopUser.setDate(registerDTO.getBirth());
        shopUser.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        return shopUser;
    }
}


