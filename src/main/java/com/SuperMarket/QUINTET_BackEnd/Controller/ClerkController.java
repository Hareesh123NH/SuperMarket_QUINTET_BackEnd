package com.SuperMarket.QUINTET_BackEnd.Controller;

import com.SuperMarket.QUINTET_BackEnd.Entity.Order;
import com.SuperMarket.QUINTET_BackEnd.Entity.Product;
import com.SuperMarket.QUINTET_BackEnd.Repository.OrderRepo;
import com.SuperMarket.QUINTET_BackEnd.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clerk")
public class ClerkController {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private ProductService productService;

    @GetMapping("/name")
    public ResponseEntity<String> welcome(){
        return ResponseEntity.ok("WelCome Clerk");
    }

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orderList = orderRepo.findAll();
        return ResponseEntity.ok(orderList);
    }

    @PutMapping("/approve/{orderId}")
    public ResponseEntity<String> orderApprove(@PathVariable long orderId) {
        Order order = orderRepo.getReferenceById(orderId);
        if (!order.getOrderStatus().equals("Pending")) {
            return new ResponseEntity<>("Already Reacted", HttpStatus.ALREADY_REPORTED);
        }
        order.setOrderStatus("Approved");
        orderRepo.save(order);
        return ResponseEntity.ok("Approved Successfully");
    }

    @PutMapping("/cancel/{orderId}")
    public ResponseEntity<String> orderCancelled(@PathVariable long orderId) {
        Order order = orderRepo.getReferenceById(orderId);
        if (!order.getOrderStatus().equals("Pending")) {
            return new ResponseEntity<>("Already Reacted", HttpStatus.ALREADY_REPORTED);
        }
        order.setOrderStatus("Cancelled");
        orderRepo.save(order);

        Product product = order.getProduct();
        int count = (int) (order.getPrice() / product.getPrice());
        product.setQuantity(product.getQuantity() + count);
        productService.save(product);


        return ResponseEntity.ok("Cancel Successfully");
    }

}
