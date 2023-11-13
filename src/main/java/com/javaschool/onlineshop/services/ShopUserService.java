package com.javaschool.onlineshop.services;

import com.javaschool.onlineshop.dto.RegisterRequestDTO;
import com.javaschool.onlineshop.dto.ShopUserRequestDTO;
import com.javaschool.onlineshop.exception.NoExistData;
import com.javaschool.onlineshop.exception.ResourceDuplicate;
import com.javaschool.onlineshop.mapper.ShopUserMapper;
import com.javaschool.onlineshop.models.RoleModel;
import com.javaschool.onlineshop.models.ShopUserModel;
import com.javaschool.onlineshop.repositories.RoleRepository;
import com.javaschool.onlineshop.repositories.ShopUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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
        ShopUserModel shopUser = createShopUserEntity(shopUserDTO, new ShopUserModel());
        if (shopUserRepository.existsByMail(shopUser.getMail())) {
            throw new ResourceDuplicate("Shop User already exists with that mail");
        }
        shopUserRepository.save(shopUser);
        return createShopUserDTO(shopUser);
    }

    @Transactional(readOnly = true)
    private RoleModel findRole(String type){
        return roleRepository.findByType(type).orElseThrow(() -> new NoExistData("This rol don't exist"));
    }

    private ShopUserRequestDTO createShopUserDTO(ShopUserModel shopUser){
        return shopUserMapper.createShopUserDTO(shopUser);
    }

    public ShopUserModel createShopUserEntity(ShopUserRequestDTO shopUserDTO, ShopUserModel shopUser){
        shopUser.setName(shopUserDTO.getName());
        shopUser.setDeleted(shopUserDTO.getIsDeleted());
        shopUser.setMail(shopUserDTO.getMail());
        shopUser.setDate(shopUserDTO.getBirth());
        shopUser.setSurname(shopUserDTO.getSurname());
        return shopUser;
    }

    public void updateShopUser(UUID uuid, ShopUserRequestDTO shopUserDTO){
        ShopUserModel shopUser = loadShopUser(uuid);
        createShopUserEntity(shopUserDTO, shopUser);
        shopUserRepository.save(shopUser);
    }

    @Transactional(readOnly = true)
    public List<ShopUserRequestDTO> getAllShopUser(){
        return shopUserRepository.findAll().stream().map(this::createShopUserDTO).toList();
    }

    @Transactional(readOnly = true)
    private ShopUserModel loadShopUser(UUID uuid){
        return shopUserRepository.findById(uuid).orElseThrow(() -> new NoExistData("Shop user don't exist"));
    }

    public ShopUserRequestDTO registerShopUser(RegisterRequestDTO registerDTO){
        if(shopUserRepository.existsByMail(registerDTO.getMail())){
            throw new ResourceDuplicate("Shop User already exists with that mail");
        }

        ShopUserModel user = shopUserMapper.createShopUserEntity(registerDTO, new ShopUserModel());
        RoleModel roles = roleRepository.findByType("USER").get();
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
        ShopUserModel user = shopUserRepository.findByMail(mail).orElseThrow(() -> new NoExistData("User not found"));
        return shopUserMapper.createShopUserDTO(user);
    }

    public ShopUserRequestDTO getShopUserbyUuid(UUID uuid){
        ShopUserModel shopUser = loadShopUser(uuid);
        return createShopUserDTO(shopUser);
    }
}
