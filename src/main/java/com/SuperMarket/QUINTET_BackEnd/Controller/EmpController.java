package com.SuperMarket.QUINTET_BackEnd.Controller;

import com.SuperMarket.QUINTET_BackEnd.Entity.Product;
import com.SuperMarket.QUINTET_BackEnd.Repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/emp")
public class EmpController {

    @Autowired
    private ProductRepo productRepo;

    @GetMapping("/name")
    public ResponseEntity<String> welcome(){
        return ResponseEntity.ok("WelCome Employee");
    }

    @GetMapping("/productsLess/{quantity}")
    public ResponseEntity<List<Product>> getProductsLessthan(@PathVariable int quantity) {
        List<Product> productList = productRepo.findByQuantityLessThan(quantity);
        return ResponseEntity.ok(productList);
    }

    @GetMapping("/getcategory/{category}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable String category) {
        List<Product> productList = productRepo.findAllBycategory(category);
        return ResponseEntity.ok(productList);
    }

    @PutMapping("/updateInventory/{productId}&{count}")
    public ResponseEntity<String> updateInventory(@PathVariable long productId, @PathVariable int count) {
        Product product = productRepo.getReferenceById(productId);
        product.setQuantity(product.getQuantity() + count);
        productRepo.save(product);
        return ResponseEntity.ok("Updated Successfully");
    }

    @PostMapping("/saveProduct")
    public ResponseEntity<String> saveProduct(@RequestBody Product product) {
        try {
            productRepo.save(product);
            return new ResponseEntity<>("Product saved succussfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Registration failed" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
