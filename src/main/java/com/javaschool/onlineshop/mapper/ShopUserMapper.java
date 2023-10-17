package com.javaschool.onlineshop.mapper;

import com.javaschool.onlineshop.dto.ShopUserRequestDTO;
import com.javaschool.onlineshop.models.ShopUser;
import org.springframework.stereotype.Service;

@Service
public class ShopUserMapper {
    public ShopUserRequestDTO createShopUserDTO(ShopUser shopUser){
        ShopUserRequestDTO shopUserDTO = new ShopUserRequestDTO();
        shopUserDTO.setName(shopUser.getName());
        shopUserDTO.setSurname(shopUser.getSurname());
        shopUserDTO.setMail(shopUser.getMail());
        shopUserDTO.setPassword(shopUser.getPassword());
        shopUserDTO.setBirth(shopUser.getDate());
        shopUserDTO.setUserRol(shopUser.getUsers_rol().getType());
        shopUserDTO.setIsDeleted(shopUser.isDeleted());
        return shopUserDTO;
    }
}
