package com.SuperMarket.QUINTET_BackEnd.Service;

import com.SuperMarket.QUINTET_BackEnd.Entity.Product;
import com.SuperMarket.QUINTET_BackEnd.Repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepo;

    public Product getById(long id){
        return productRepo.getReferenceById(id);
    }
    public List<Product> getAllProducts(){
        return productRepo.findAll();
    }

    public List<Product> getProductsByCategory(String category){
        return productRepo.findAllBycategory(category);
    }

    public void save(Product product){
        productRepo.save(product);
    }
}
