package com.SuperMarket.QUINTET_BackEnd.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class BillProduct {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String pName;
    private String category;

    private int quantity;
    private double unitPrice;
    private double productPrice;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bill_id")
    private Bills bills;

    public BillProduct() {
    }

    public BillProduct(String pName, String category, int quantity, double unitPrice, double productPrice, Bills bills) {
        this.pName = pName;
        this.category = category;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.productPrice = productPrice;
        this.bills = bills;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public Bills getBills() {
        return bills;
    }

    public void setBills(Bills bills) {
        this.bills = bills;
    }
}
