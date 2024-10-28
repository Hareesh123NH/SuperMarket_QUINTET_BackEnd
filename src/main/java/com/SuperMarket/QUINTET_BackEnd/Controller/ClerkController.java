package com.SuperMarket.QUINTET_BackEnd.Controller;

import com.SuperMarket.QUINTET_BackEnd.Entity.Order;
import com.SuperMarket.QUINTET_BackEnd.Repository.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/clerk")
public class ClerkController {

    @Autowired
    private OrderRepo orderRepo;

    @GetMapping("/name")
    public ResponseEntity<String> welcome(){
        return ResponseEntity.ok("WelCome Clerk");
    }

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orderList = orderRepo.findAll();
//        for (Order order : orderList) {
//            System.out.println(order.getUser().getUsername());
//        }
        return ResponseEntity.ok(orderList);
    }



}
