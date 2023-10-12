package com.javaschool.onlineshop.Controllers;

import com.javaschool.onlineshop.DTO.RoleRequestDTO;
import com.javaschool.onlineshop.DTO.StatusRequestDTO;
import com.javaschool.onlineshop.Models.Role;
import com.javaschool.onlineshop.Models.Status;
import com.javaschool.onlineshop.Services.RoleService;
import com.javaschool.onlineshop.Services.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
public class RoleController {
    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService){
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<String> createRole(@RequestBody RoleRequestDTO roleDTO) {
        Role role = new Role();
        role.setType(roleDTO.getType());
        role.setDeleted(false);

        roleService.saveRole(role);

        return ResponseEntity.ok("Role created with ID: " + role.getRole_uuid());
    }
}
