package com.SuperMarket.QUINTET_BackEnd.Controller;

import com.SuperMarket.QUINTET_BackEnd.Dto.UserDto;
import com.SuperMarket.QUINTET_BackEnd.Entity.Order;
import com.SuperMarket.QUINTET_BackEnd.Entity.Role;
import com.SuperMarket.QUINTET_BackEnd.Entity.User;
import com.SuperMarket.QUINTET_BackEnd.Repository.OrderRepo;
import com.SuperMarket.QUINTET_BackEnd.Repository.RolesRepo;
import com.SuperMarket.QUINTET_BackEnd.Repository.UserRepo;
import com.SuperMarket.QUINTET_BackEnd.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manager")
public class ManagerController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RolesRepo rolesRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderRepo orderRepo;


    @GetMapping("/name")
    public ResponseEntity<String> getAdmin(){
        return ResponseEntity.ok("WelCome Manager");
    }

    @GetMapping("/getClerks")
    public ResponseEntity<List<User>> getClerks() {
        List<User> clerks = userRepo.findByRoleName((long) 3);
        return ResponseEntity.ok(clerks);
    }

    @GetMapping("/getEmps")
    public ResponseEntity<List<User>> getEmployees() {
        List<User> emps = userRepo.findByRoleName((long) 4);
        return ResponseEntity.ok(emps);
    }

    @PostMapping("/register/{roleId}")
    public ResponseEntity<String> registerUser(@RequestBody UserDto userDto, @PathVariable int roleId) {
        try {

            User user = new User();
            user.setUsername(userDto.getName());
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));

            Role role = rolesRepo.getReferenceById((long) roleId);
            user.setRole(role);

            userService.saveUser(user);
            return new ResponseEntity<>("User register succussfully", HttpStatus.CREATED);
        }
        catch (Exception e) {
            return new ResponseEntity<>("Registration failed" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/allorders")
    public ResponseEntity<List<Order>> getAllorders() {
        List<Order> orderList = orderRepo.findAll();
        return ResponseEntity.ok(orderList);
    }
}
