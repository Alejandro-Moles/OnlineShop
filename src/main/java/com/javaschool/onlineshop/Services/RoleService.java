package com.javaschool.onlineshop.Services;

import com.javaschool.onlineshop.Models.Role;
import com.javaschool.onlineshop.Repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }

    public void saveRole(Role role){
        roleRepository.save(role);
    }
}
