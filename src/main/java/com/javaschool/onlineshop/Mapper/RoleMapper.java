package com.javaschool.onlineshop.Mapper;

import com.javaschool.onlineshop.DTO.RoleRequestDTO;
import com.javaschool.onlineshop.Models.Role;
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
