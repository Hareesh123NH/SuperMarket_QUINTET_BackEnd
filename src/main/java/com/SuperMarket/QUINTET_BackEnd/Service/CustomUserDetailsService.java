package com.SuperMarket.QUINTET_BackEnd.Service;

import com.SuperMarket.QUINTET_BackEnd.Entity.Role;
import com.SuperMarket.QUINTET_BackEnd.Entity.User;
import com.SuperMarket.QUINTET_BackEnd.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepo.findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("User Not Found with username "+username));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                mapRolesToAuthorities((user.getRole()))
        );
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Role role){
        return Collections.singleton(new SimpleGrantedAuthority(role.getName()));
    }

}
