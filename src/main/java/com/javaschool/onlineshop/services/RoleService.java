package com.javaschool.onlineshop.services;

import com.javaschool.onlineshop.dto.RoleRequestDTO;
import com.javaschool.onlineshop.exception.ResourceDuplicate;
import com.javaschool.onlineshop.mapper.RoleMapper;
import com.javaschool.onlineshop.models.RoleModel;
import com.javaschool.onlineshop.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public RoleRequestDTO saveRole(RoleRequestDTO roleDTO){
        RoleModel role = createRoleEntity(roleDTO, new RoleModel());
        if (roleRepository.existsByType(role.getType())) {
            throw new ResourceDuplicate("Role already exists");
        }
        roleRepository.save(role);
        return createRoleDTO(role);
    }

    private RoleRequestDTO createRoleDTO(RoleModel role){
        return roleMapper.createRoleDTO(role);
    }

    private RoleModel createRoleEntity(RoleRequestDTO roleDTO, RoleModel role){
        role.setType(roleDTO.getType());
        role.setDeleted(false);
        return role;
    }
}
