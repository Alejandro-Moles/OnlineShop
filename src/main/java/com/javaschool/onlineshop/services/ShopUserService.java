package com.javaschool.onlineshop.services;

import com.javaschool.onlineshop.dto.*;
import com.javaschool.onlineshop.exception.NoExistData;
import com.javaschool.onlineshop.exception.OldPasswordNotSame;
import com.javaschool.onlineshop.exception.ResourceDuplicate;
import com.javaschool.onlineshop.mapper.ShopUserMapper;
import com.javaschool.onlineshop.models.RoleModel;
import com.javaschool.onlineshop.models.ShopUserModel;
import com.javaschool.onlineshop.repositories.RoleRepository;
import com.javaschool.onlineshop.repositories.ShopUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShopUserService {

    private final ShopUserRepository shopUserRepository;
    private final ShopUserMapper shopUserMapper;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    // Maps a ShopUserModel to a ShopUserRequestDTO
    private ShopUserRequestDTO createShopUserDTO(ShopUserModel shopUser) {
        return shopUserMapper.createShopUserDTO(shopUser);
    }

    // Creates a ShopUserModel entity from a ShopUserRequestDTO
    public ShopUserModel createShopUserEntity(ShopUserRequestDTO shopUserDTO, ShopUserModel shopUser) {
        shopUser.setName(shopUserDTO.getName());
        shopUser.setDeleted(shopUserDTO.getIsDeleted());
        shopUser.setMail(shopUserDTO.getMail());
        shopUser.setDate(shopUserDTO.getBirth());
        shopUser.setSurname(shopUserDTO.getSurname());
        return shopUser;
    }

    // Retrieves a ShopUser by UUID and maps it to a ShopUserRequestDTO
    public ShopUserRequestDTO getShopUserbyUuid(UUID uuid) {
        ShopUserModel shopUser = loadShopUser(uuid);
        return createShopUserDTO(shopUser);
    }

    // Checks if the provided old password matches the current password for the user
    private boolean checkNewPassword(UUID uuid, NewPasswordDTO passwordDTO) {
        ShopUserModel userModel = loadShopUser(uuid);
        return passwordEncoder.matches(passwordDTO.getOldPassword(), userModel.getPassword());
    }

    // Saves a new ShopUser to the database
    @Transactional
    public ShopUserRequestDTO saveShopUser(ShopUserRequestDTO shopUserDTO) {
        ShopUserModel shopUser = createShopUserEntity(shopUserDTO, new ShopUserModel());
        if (shopUserRepository.existsByMail(shopUser.getMail())) {
            throw new ResourceDuplicate("Shop User already exists with that mail");
        }
        shopUserRepository.save(shopUser);
        return createShopUserDTO(shopUser);
    }

    // Updates the password for a ShopUser
    @Transactional
    public void updateShopUserPassword(UUID uuid, NewPasswordDTO passwordDTO) {
        ShopUserModel shopUser = loadShopUser(uuid);
        if (checkNewPassword(uuid, passwordDTO)) {
            String newPassword = passwordEncoder.encode(passwordDTO.getNewPassword());
            shopUser.setPassword(newPassword);
            shopUserRepository.save(shopUser);
        } else {
            throw new OldPasswordNotSame("The old password is not the same");
        }
    }

    @Transactional
    public void updateShopUserData(UUID uuid, ProfileDataDTO profileDataDTO) {
        ShopUserModel shopUser = loadShopUser(uuid);
        if (shopUserRepository.existsByMail(profileDataDTO.getMail())) {
            throw new ResourceDuplicate("Shop User already exists with that mail");
        }
        shopUser.setMail(profileDataDTO.getMail());
        shopUser.setName(profileDataDTO.getName());
        shopUser.setSurname(profileDataDTO.getSurName());
        shopUserRepository.save(shopUser);
    }

    // Retrieves all ShopUsers from the database
    @Transactional(readOnly = true)
    public List<ShopUserRequestDTO> getAllShopUser() {
        return shopUserRepository.findAll().stream().map(this::createShopUserDTO).toList();
    }

    // Retrieves a ShopUser by UUID
    @Transactional(readOnly = true)
    private ShopUserModel loadShopUser(UUID uuid) {
        return shopUserRepository.findById(uuid).orElseThrow(() -> new NoExistData("Shop user don't exist"));
    }

    // Registers a new ShopUser with the provided details
    @Transactional
    public ShopUserRequestDTO registerShopUser(RegisterRequestDTO registerDTO) {
        if (shopUserRepository.existsByMail(registerDTO.getMail())) {
            throw new ResourceDuplicate("Shop User already exists with that mail");
        }

        ShopUserModel user = shopUserMapper.createShopUserEntity(registerDTO, new ShopUserModel());
        RoleModel roles = roleRepository.findByType("USER").get();
        user.setRoles(Collections.singletonList(roles));
        shopUserRepository.save(user);
        return createShopUserDTO(user);
    }

    // Retrieves the currently authenticated user
    @Transactional(readOnly = true)
    public ShopUserRequestDTO getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new NoExistData("User not found");
        }
        String mail = authentication.getName();
        ShopUserModel user = shopUserRepository.findByMail(mail).orElseThrow(() -> new NoExistData("User not found"));
        return shopUserMapper.createShopUserDTO(user);
    }

    // Retrieves user statistics for the provided mail
    @Transactional(readOnly = true)
    public UserStatisticsDTO getUserStatistic(String userMail) {
        Optional<ShopUserModel> shopUserModel = shopUserRepository.findByMail(userMail);
        if (shopUserModel.isPresent()) {
            ShopUserModel userModel = shopUserModel.get();
            if (userModel.getOrders() != null && !userModel.getOrders().isEmpty()) {
                return shopUserRepository.getUserStatistics(userModel.getUserUuid());
            } else {
                return new UserStatisticsDTO(0L, 0.0, 0L);
            }
        } else {
            return null;
        }
    }

    // Assigns roles to a user
    @Transactional
    public void assignRolesToUser(UUID userUuid, List<String> roleTypes) {
        ShopUserModel shopUser = loadShopUser(userUuid);
        List<RoleModel> roles = roleRepository.findByTypeIn(roleTypes);
        if (roles.isEmpty()) {
            throw new NoExistData("No roles found with the provided types");
        }

        shopUser.setRoles(roles);
        shopUserRepository.save(shopUser);

        createShopUserDTO(shopUser);
    }
}
