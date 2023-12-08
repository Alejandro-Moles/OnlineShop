package com.javaschool.onlineshop.security;

import com.javaschool.onlineshop.exception.NoExistData;
import com.javaschool.onlineshop.models.RoleModel;
import com.javaschool.onlineshop.models.ShopUserModel;
import com.javaschool.onlineshop.repositories.ShopUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailService implements UserDetailsService {

    // Injecting ShopUserRepository using constructor injection
    private final ShopUserRepository shopUserRepository;

    @Autowired
    public CustomUserDetailService(ShopUserRepository shopUserRepository){
        this.shopUserRepository = shopUserRepository;
    }

    // This method is required by the UserDetailsService interface.
    // It loads a user by their email address and converts it to a Spring Security UserDetails object.
    @Override
    public UserDetails loadUserByUsername(String userMail) throws UsernameNotFoundException {
        // Fetching a user from the repository based on the provided email
        ShopUserModel user = shopUserRepository.findByMail(userMail)
                .orElseThrow(() -> new NoExistData("User doesn't exist"));

        // Creating a UserDetails object with the user's email, password, and authorities (roles)
        return new User(user.getMail(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    // This method maps RoleModel objects to Spring Security GrantedAuthority objects.
    private Collection<GrantedAuthority> mapRolesToAuthorities(List<RoleModel> roles){
        // Converting the list of RoleModel objects to a collection of GrantedAuthority objects
        // Each role's type is used as the authority for the user
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getType()))
                .collect(Collectors.toList());
    }
}
