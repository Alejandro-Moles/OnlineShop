package com.javaschool.onlineshop.mapper;

import com.javaschool.onlineshop.dto.RoleRequestDTO;
import com.javaschool.onlineshop.models.Role;
import org.springframework.stereotype.Service;

@Service
public class RoleMapper {
    public RoleRequestDTO createRoleDTO(Role role){
        RoleRequestDTO roleDTO = new RoleRequestDTO();
        roleDTO.setType(role.getType());
        roleDTO.setDeleted(role.isDeleted());

        return roleDTO;
    }
}
