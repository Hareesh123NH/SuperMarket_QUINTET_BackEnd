package com.SuperMarket.QUINTET_BackEnd.Controller;

import com.SuperMarket.QUINTET_BackEnd.Entity.Order;
import com.SuperMarket.QUINTET_BackEnd.Entity.Product;
import com.SuperMarket.QUINTET_BackEnd.Entity.UserCart;
import com.SuperMarket.QUINTET_BackEnd.Entity.User;
import com.SuperMarket.QUINTET_BackEnd.Repository.OrderRepo;
import com.SuperMarket.QUINTET_BackEnd.Repository.UserCartRepo;
import com.SuperMarket.QUINTET_BackEnd.Repository.UserRepo;
import com.SuperMarket.QUINTET_BackEnd.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserCartRepo userCartRepo;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderRepo orderRepo;

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

    @GetMapping("/getProducts")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> productList = productService.getAllProducts();
        return ResponseEntity.ok(productList);
    }

    @GetMapping("/getProducts/{category}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable String category) {
        List<Product> productList = productService.getProductsByCategory(category);
        return ResponseEntity.ok(productList);
    }

    @PostMapping("/addTocart/{userId}&{productId}")
    public ResponseEntity<String> addToCart(@PathVariable long userId, @PathVariable long productId) {
        try {

            User user = userRepo.getReferenceById(userId);
            Product product = productService.getById(productId);

            Optional<UserCart> existingCart = userCartRepo.findByUserAndProduct(user, product);

            if (existingCart.isPresent()) {
                return new ResponseEntity<>("Product is already in the cart", HttpStatus.BAD_REQUEST);
            }

            UserCart userCart = new UserCart(product, user);
            userCartRepo.save(userCart);
            return new ResponseEntity<>("Added to cart Seccussfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to add to cart" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/cart/{userId}")
    public ResponseEntity<List<UserCart>> getAllCart(@PathVariable long userId) {
        List<UserCart> userCartList = userCartRepo.findByUserId(userId);
        return ResponseEntity.ok(userCartList);
    }


    @DeleteMapping("/removeFromCart/{userId}&{productId}")
    public ResponseEntity<String> removeFromCart(@PathVariable long userId, @PathVariable long productId) {
        try {
            User user = userRepo.getReferenceById(userId);
            Product product = productService.getById(productId);

            // Find the cart entry to delete
            Optional<UserCart> existingCart = userCartRepo.findByUserAndProduct(user, product);

            if (existingCart.isPresent()) {
                userCartRepo.delete(existingCart.get());
                return new ResponseEntity<>("Removed from cart successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("This product is not in your cart", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to remove from cart: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addToOrders/{userId}&{productId}&{price}")
    public ResponseEntity<String> addToOrders(@PathVariable long userId, @PathVariable long productId, @PathVariable float price) {
        try {

            User user = userRepo.getReferenceById(userId);
            Product product = productService.getById(productId);

            Order order = new Order(price, user, product);
            orderRepo.save(order);
            removeFromCart(userId, productId);

            int count=(int)(price/product.getPrice());
            product.setQuantity(product.getQuantity()-count);
            productService.save(product);

            return new ResponseEntity<>("Added to Orders Seccussfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to add to orders" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/cancelOrder/{orderId}")
    public ResponseEntity<String> cancelOrder(@PathVariable long orderId) {
        try {

            Optional<Order> existingOrder = orderRepo.findById(orderId);

            if (existingOrder.isPresent()) {
                Order order = existingOrder.get();
                Product product = order.getProduct();
                int count = (int) (order.getPrice() / product.getPrice());
                product.setQuantity(product.getQuantity() + count);
                orderRepo.delete(existingOrder.get());
                return new ResponseEntity<>("Cancel Order successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("This product is not in your orders", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to cancel order " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/orders/{userId}")
    public ResponseEntity<List<Order>> getOrders(@PathVariable long userId) {
        List<Order> orderList = orderRepo.findAllByuserId(userId);
        return ResponseEntity.ok(orderList);
    }


}
