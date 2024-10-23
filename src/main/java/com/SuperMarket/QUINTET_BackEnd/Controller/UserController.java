package com.SuperMarket.QUINTET_BackEnd.Controller;

import com.SuperMarket.QUINTET_BackEnd.Entity.UserCart;
import com.SuperMarket.QUINTET_BackEnd.Entity.User;
import com.SuperMarket.QUINTET_BackEnd.Repository.UserCartRepo;
import com.SuperMarket.QUINTET_BackEnd.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private UserCartRepo userCartRepo;

    @GetMapping("/name")
    public ResponseEntity<String> getUser(){
        return ResponseEntity.ok("Welcome User");
    }

    @GetMapping("/profile")
    public ResponseEntity<User> getUser(Authentication authentication){
        UserDetails userDetails=(UserDetails) authentication.getPrincipal();
        String username= userDetails.getUsername();
        User user=userRepo.findUserByUsername(username);
        return ResponseEntity.ok(user);
    }

//    @PostMapping("/add-rental")
//    public ResponseEntity<String> addToRental(@RequestParam("carId") int carId,@RequestParam("userId") long userId){
//        Car car=carRepo.getReferenceById(carId);
//        User user=userRepo.getReferenceById(userId);
//        RentalCars rentalCars=new RentalCars(car.getId(), car.getName(), car.getCompany(), car.getModel(), car.getColor(),user);
//        rentalRepo.save(rentalCars);
//        car.setAvail(false);
//        return ResponseEntity.ok("Successfully Rented");
//    }

    @GetMapping("allrentals")
    public ResponseEntity<List<UserCart>> getAllRentals(){
        List<UserCart> rentalCars= userCartRepo.findAll();
        return ResponseEntity.ok(rentalCars);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletecar(@PathVariable int id){
        userCartRepo.deleteById(id);
        return ResponseEntity.ok("Deleted SuccussFully");
    }

}
