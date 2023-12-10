package com.javaschool.onlineshop.init;

import com.javaschool.onlineshop.models.RoleModel;
import com.javaschool.onlineshop.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RoleDataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        insertRoles();
    }

    private void insertRoles(){
        if (roleRepository.count() == 0) {
            RoleModel roleUser = new RoleModel();
            roleUser.setRoleUuid(UUID.randomUUID());
            roleUser.setType("USER");
            roleUser.setDeleted(false);
            roleRepository.save(roleUser);

            RoleModel roleAdmin = new RoleModel();
            roleAdmin.setRoleUuid(UUID.randomUUID());
            roleAdmin.setType("ADMIN");
            roleAdmin.setDeleted(false);
            roleRepository.save(roleAdmin);

            RoleModel roleEmployee = new RoleModel();
            roleEmployee.setRoleUuid(UUID.randomUUID());
            roleEmployee.setType("EMPLOYEE");
            roleEmployee.setDeleted(false);
            roleRepository.save(roleEmployee);

        }
    }
}
