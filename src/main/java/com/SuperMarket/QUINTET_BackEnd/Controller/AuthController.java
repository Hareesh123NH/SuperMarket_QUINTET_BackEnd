package com.SuperMarket.QUINTET_BackEnd.Controller;

import com.SuperMarket.QUINTET_BackEnd.Dto.UserDto;
import com.SuperMarket.QUINTET_BackEnd.Entity.Role;
import com.SuperMarket.QUINTET_BackEnd.Entity.User;
import com.SuperMarket.QUINTET_BackEnd.Repository.RolesRepo;
import com.SuperMarket.QUINTET_BackEnd.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RolesRepo rolesRepo;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDto userDto){
        try{
            User user=new User();
            user.setUsername(userDto.getName());
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));

            Role role=rolesRepo.getReferenceById((long)1);
            user.setRole(role);

            userService.saveUser(user);
            return new ResponseEntity<>("User register succussfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Registration failed"+e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
