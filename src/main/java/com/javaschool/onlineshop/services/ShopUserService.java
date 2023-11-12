package com.javaschool.onlineshop.services;

import com.javaschool.onlineshop.dto.ProductRequestDTO;
import com.javaschool.onlineshop.dto.RegisterRequestDTO;
import com.javaschool.onlineshop.dto.ShopUserRequestDTO;
import com.javaschool.onlineshop.exception.NoExistData;
import com.javaschool.onlineshop.exception.ResourceDuplicate;
import com.javaschool.onlineshop.mapper.ShopUserMapper;
import com.javaschool.onlineshop.models.Products;
import com.javaschool.onlineshop.models.Role;
import com.javaschool.onlineshop.models.ShopUser;
import com.javaschool.onlineshop.repositories.RoleRepository;
import com.javaschool.onlineshop.repositories.ShopUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

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

    private ShopUser createShopUserEntity(ShopUserRequestDTO shopUserDTO, ShopUser shopUser){
        shopUser.setName(shopUserDTO.getName());
        shopUser.setDeleted(shopUserDTO.getIsDeleted());
        shopUser.setMail(shopUserDTO.getMail());
        shopUser.setDate(shopUserDTO.getBirth());
        shopUser.setSurname(shopUserDTO.getSurname());
        return shopUser;
    }

    public void updateShopUser(UUID uuid, ShopUserRequestDTO shopUserDTO){
        ShopUser shopUser = loadShopUser(uuid);
        createShopUserEntity(shopUserDTO, shopUser);
        shopUserRepository.save(shopUser);
    }

    @Transactional(readOnly = true)
    public List<ShopUserRequestDTO> getAllShopUser(){
        return shopUserRepository.findAll().stream().map(this::createShopUserDTO).toList();
    }

    @Transactional(readOnly = true)
    private ShopUser loadShopUser(UUID uuid){
        return shopUserRepository.findById(uuid).orElseThrow(() -> new NoExistData("Shop user don't exist"));
    }

    public ShopUserRequestDTO registerShopUser(RegisterRequestDTO registerDTO){
        if(shopUserRepository.existsByMail(registerDTO.getMail())){
            throw new ResourceDuplicate("Shop User already exists with that mail");
        }

        ShopUser user = shopUserMapper.createShopUserEntity(registerDTO, new ShopUser());
        Role roles = roleRepository.findByType("USER").get();
        user.setRoles(Collections.singletonList(roles));
        shopUserRepository.save(user);
        return createShopUserDTO(user);
    }

    public ShopUserRequestDTO getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || !authentication.isAuthenticated()){
            throw  new NoExistData("User not found");
        }
        String mail = authentication.getName();
        ShopUser user = shopUserRepository.findByMail(mail).orElseThrow(() -> new NoExistData("User not found"));
        return shopUserMapper.createShopUserDTO(user);
    }

    public ShopUserRequestDTO getShopUserbyUuid(UUID uuid){
        ShopUser shopUser = loadShopUser(uuid);
        return createShopUserDTO(shopUser);
    }
}
