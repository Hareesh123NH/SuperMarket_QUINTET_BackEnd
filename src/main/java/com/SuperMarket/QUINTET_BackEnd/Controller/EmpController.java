package com.SuperMarket.QUINTET_BackEnd.Controller;

import com.SuperMarket.QUINTET_BackEnd.Entity.Bills;
import com.SuperMarket.QUINTET_BackEnd.Entity.Product;
import com.SuperMarket.QUINTET_BackEnd.Entity.User;
import com.SuperMarket.QUINTET_BackEnd.Repository.BillRepo;
import com.SuperMarket.QUINTET_BackEnd.Repository.ProductRepo;
import com.SuperMarket.QUINTET_BackEnd.Repository.UserRepo;
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

    @Autowired
    private BillRepo billRepo;

    @Autowired
    private UserRepo userRepo;

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

    @GetMapping("/allProducts")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> productList = productRepo.findAllByOrderByQuantityAsc();
        return ResponseEntity.ok(productList);
    }

    @PutMapping("/updateProduct/{productId}")
    public ResponseEntity<String> updateProduct(@PathVariable long productId, @RequestBody Product product) {
        Product oldproduct = productRepo.getReferenceById(productId);

        oldproduct.setQuantity(product.getQuantity());
        oldproduct.setName(product.getName());
        oldproduct.setCategory(product.getCategory());
        oldproduct.setPrice(product.getPrice());

        productRepo.save(oldproduct);

        return ResponseEntity.ok("Updated Successfully");
    }

//    @DeleteMapping("/removeProduct/{productId}")
//    public ResponseEntity<String> removeProduct(@PathVariable long productId) {
//        Product product = productRepo.getReferenceById(productId);
//
//        productRepo.delete(product);
//        return ResponseEntity.ok("Removed Successfully");
//    }
@GetMapping("/allBills")
public ResponseEntity<List<Bills>> getAllBills() {
    List<Bills> billList = billRepo.findAll();
    return ResponseEntity.ok(billList);
}

}
