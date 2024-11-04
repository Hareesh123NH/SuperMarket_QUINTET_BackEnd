package com.SuperMarket.QUINTET_BackEnd.Controller;

import com.SuperMarket.QUINTET_BackEnd.Entity.*;
import com.SuperMarket.QUINTET_BackEnd.Repository.BillRepo;
import com.SuperMarket.QUINTET_BackEnd.Repository.OrderRepo;
import com.SuperMarket.QUINTET_BackEnd.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clerk")
public class ClerkController {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private BillRepo billRepo;

    @Autowired
    private ProductService productService;

    @GetMapping("/name")
    public ResponseEntity<String> welcome(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Authentication: " + auth);
        if (auth != null && auth.isAuthenticated()) {
            return ResponseEntity.ok("Authenticated as " + auth.getName());
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not authenticated");
    }

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orderList = orderRepo.findAll();
        return ResponseEntity.ok(orderList);
    }

    @GetMapping("/orders/{status}")
    public ResponseEntity<List<Order>> getAllPending(@PathVariable String status) {
        List<Order> orderList = orderRepo.findAllByorderStatus(status);
        return ResponseEntity.ok(orderList);
    }

    @PutMapping("/approve/{orderId}")
    public ResponseEntity<String> orderApprove(@PathVariable long orderId) {
        Order order = orderRepo.getReferenceById(orderId);
        Optional<Order> isExit = orderRepo.findById(orderId);
        if (isExit.isEmpty()) {
            return new ResponseEntity<>("Order Not Availble", HttpStatus.NOT_FOUND);
        }
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
        Optional<Order> isExit = orderRepo.findById(orderId);
        if (isExit.isEmpty()) {
            return new ResponseEntity<>("Order Not Availble", HttpStatus.NOT_FOUND);
        }
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

    @PostMapping("/addBill")
    public ResponseEntity<String> saveBills(@RequestBody Bills bill) {
        try {
            bill.getBillProducts().forEach(item -> item.setBills(bill));

            billRepo.save(bill);
            return new ResponseEntity<>("Bill register succussfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Registration failed" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/generateBill/{orderId}")
    public ResponseEntity<String> generateBill(@PathVariable long orderId) {
        Order order = orderRepo.getReferenceById(orderId);
        Optional<Order> isExit = orderRepo.findById(orderId);

        if (isExit.isEmpty()) {
            return new ResponseEntity<>("Order Not Availble", HttpStatus.NOT_FOUND);
        }

        order.setOrderStatus("BillGenerated");
        orderRepo.save(order);
        return ResponseEntity.ok("Bill Generated Successfully");
    }

    @DeleteMapping("/delete/{orderId}")
    public ResponseEntity<String> deleteorder(@PathVariable long orderId) {
        Order order = orderRepo.getReferenceById(orderId);
        Optional<Order> isExit = orderRepo.findById(orderId);

        if (isExit.isEmpty()) {
            return new ResponseEntity<>("Order Not Availble", HttpStatus.NOT_FOUND);
        }

        orderRepo.delete(order);
        return ResponseEntity.ok("Order Deleted Successfully");
    }


}
