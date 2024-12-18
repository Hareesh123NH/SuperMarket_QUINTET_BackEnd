package com.SuperMarket.QUINTET_BackEnd.Service;

import com.SuperMarket.QUINTET_BackEnd.Entity.User;
import com.SuperMarket.QUINTET_BackEnd.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public void saveUser(User user){
        userRepo.save(user);
    }

    public Optional<User> findByusername(String username){
        return userRepo.findByUsername(username);
    }

    public User findByname(String name){
        return userRepo.findUserByUsername(name);
    }
}
