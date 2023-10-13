package com.javaschool.onlineshop.Services;

import com.javaschool.onlineshop.DTO.RoleRequestDTO;
import com.javaschool.onlineshop.Mapper.RoleMapper;
import com.javaschool.onlineshop.Models.Role;
import com.javaschool.onlineshop.Repositories.RoleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public RoleRequestDTO saveRole(RoleRequestDTO roleDTO){
        Role role = new Role();
        role.setType(roleDTO.getType());
        role.setDeleted(false);

        roleRepository.save(role);
        return createRoleDTO(role);
    }

    private RoleRequestDTO createRoleDTO(Role role){
        return roleMapper.createRoleDTO(role);
    }
}
