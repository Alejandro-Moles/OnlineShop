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

    private final ShopUserRepository shopUserRepository;

    @Autowired
    public CustomUserDetailService(ShopUserRepository shopUserRepository){
        this.shopUserRepository = shopUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userMail) throws UsernameNotFoundException {
        ShopUserModel user = shopUserRepository.findByMail(userMail).orElseThrow(() -> new NoExistData("User don't exist"));
        return new User(user.getMail(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<GrantedAuthority> mapRolesToAuthorities(List<RoleModel> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getType())).collect(Collectors.toList());
    }
}
