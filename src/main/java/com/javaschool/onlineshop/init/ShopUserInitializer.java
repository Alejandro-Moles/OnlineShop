package com.javaschool.onlineshop.init;

import com.javaschool.onlineshop.models.RoleModel;
import com.javaschool.onlineshop.models.ShopUserModel;
import com.javaschool.onlineshop.repositories.RoleRepository;
import com.javaschool.onlineshop.repositories.ShopUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ShopUserInitializer implements CommandLineRunner {

    private final ShopUserRepository shopUserRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        insertUsers();
    }

    private void insertUsers() {
        if (shopUserRepository.count() == 0) {
            ShopUserModel user = new ShopUserModel();
            user.setUserUuid(UUID.randomUUID());
            user.setName("User");
            user.setSurname("Admin");
            user.setDate(new Date());
            user.setMail("admin@example.com");

            List<RoleModel> roles = roleRepository.findByTypeIn(List.of("ADMIN", "USER", "EMPLOYEE"));
            if (roles.isEmpty()) {
                throw new RuntimeException("Roles not found");
            }
            user.setRoles(roles);

            String encryptedPassword = passwordEncoder.encode("root");
            user.setPassword(encryptedPassword);

            shopUserRepository.save(user);
        }
    }
}
