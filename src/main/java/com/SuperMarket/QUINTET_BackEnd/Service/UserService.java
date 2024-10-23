package com.SuperMarket.QUINTET_BackEnd.Service;

import com.SuperMarket.QUINTET_BackEnd.Entity.User;
import com.SuperMarket.QUINTET_BackEnd.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public void saveUser(User user){
        userRepo.save(user);
    }
}
