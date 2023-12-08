package com.javaschool.onlineshop.controllers;

import com.javaschool.onlineshop.dto.PlatformsRequestDTO;
import com.javaschool.onlineshop.dto.RoleRequestDTO;
import com.javaschool.onlineshop.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    // Endpoint to create a role
    @PostMapping
    public ResponseEntity<String> createRole(@RequestBody RoleRequestDTO roleDTO) {
        RoleRequestDTO result = roleService.saveRole(roleDTO);
        return ResponseEntity.ok("Role created: " + result.getType());
    }

    // Endpoint to get all roles
    @GetMapping
    public ResponseEntity<List<RoleRequestDTO>> getAllRoles() {
        List<RoleRequestDTO> result = roleService.getAllRoles();
        return ResponseEntity.ok(result);
    }
}
