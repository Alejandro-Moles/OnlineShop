package com.javaschool.onlineshop.mapper;

import com.javaschool.onlineshop.dto.RoleRequestDTO;
import com.javaschool.onlineshop.models.RoleModel;
import org.springframework.stereotype.Service;

@Service
public class RoleMapper {
    public RoleRequestDTO createRoleDTO(RoleModel role){
        RoleRequestDTO roleDTO = new RoleRequestDTO();
        roleDTO.setUuid(role.getRoleUuid());
        roleDTO.setType(role.getType());
        roleDTO.setDeleted(role.isDeleted());

        return roleDTO;
    }
}
