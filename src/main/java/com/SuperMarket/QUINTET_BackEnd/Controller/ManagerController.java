package com.SuperMarket.QUINTET_BackEnd.Controller;

import com.SuperMarket.QUINTET_BackEnd.Dto.UserDto;
import com.SuperMarket.QUINTET_BackEnd.Entity.Order;
import com.SuperMarket.QUINTET_BackEnd.Entity.Role;
import com.SuperMarket.QUINTET_BackEnd.Entity.User;
import com.SuperMarket.QUINTET_BackEnd.Entity.UserProfile;
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
import java.util.Optional;

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
            user.setUsername(userDto.getUsername());
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));

            Role role = rolesRepo.getReferenceById((long) roleId);
            user.setRole(role);

            UserProfile userProfile = new UserProfile();
            userProfile.setFullName(userDto.getFullName());
            userProfile.setPhoneNumber(userDto.getPhoneNumber());
            userProfile.setAddress(userDto.getAddress());
            userProfile.setEmail(userDto.getEmail());

            user.setUserProfile(userProfile);

            userService.saveUser(user);
            return new ResponseEntity<>("User register succussfully", HttpStatus.CREATED);
        }
        catch (Exception e) {
            return new ResponseEntity<>("Registration failed" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/allorders")
    public ResponseEntity<List<Order>> getAllorders() {
        List<Order> orderList = orderRepo.findAllByorderStatusNot("Pending");
        return ResponseEntity.ok(orderList);
    }

    @PutMapping("/update/{profileId}")
    public ResponseEntity<String> updateProfile(@PathVariable long profileId, @RequestBody UserProfile userProfile) {
        User user = userRepo.findByprofileId(profileId);
        UserProfile userdetails = user.getUserProfile();

        userdetails.setEmail(userProfile.getEmail());
        userdetails.setAddress(userProfile.getAddress());
        userdetails.setPhoneNumber(userProfile.getPhoneNumber());
        userdetails.setFullName(userProfile.getFullName());

        user.setUserProfile(userdetails);

        userRepo.save(user);
        return ResponseEntity.ok("Updated Successfully");
    }

    @DeleteMapping("/remove/{profileId}")
    public ResponseEntity<String> removeProfile(@PathVariable long profileId) {
        try {

            Optional<User> isExist = userRepo.findUserByprofileId(profileId);
            if (isExist.isEmpty()) {
                return new ResponseEntity<>("User Not Found", HttpStatus.NOT_FOUND);
            }
            userRepo.delete(isExist.get());
            return ResponseEntity.ok("Successfully Deleted User");
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete User :" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteProfile(@PathVariable long userId) {
        try {

            Optional<User> isExist = userRepo.findById(userId);
            if (isExist.isEmpty()) {
                return new ResponseEntity<>("User Not Found", HttpStatus.NOT_FOUND);
            }
            userRepo.delete(isExist.get());
            return ResponseEntity.ok("Successfully Deleted User");
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete User :" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

//    @GetMapping("/getAllusers")
//    public ResponseEntity<List<User>> getAllUsers() {
//        List<User> users = userRepo.findAll();
//        return ResponseEntity.ok(users);
//    }
//
//    @GetMapping("/getOrdersbyIds")
//    public ResponseEntity<List<Order>> getOrdersbyids(@RequestParam List<Long> orderIds) {
//        List<Order> orders = orderRepo.findAllById(orderIds);
//        return ResponseEntity.ok((orders));
//    }

}
