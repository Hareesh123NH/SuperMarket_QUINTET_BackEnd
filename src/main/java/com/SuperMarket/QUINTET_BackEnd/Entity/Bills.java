package com.SuperMarket.QUINTET_BackEnd.Entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Bills {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String customerName;

    private String transactionId;

    private double totalPrice;

    private double gstAmount;

    private double payableAmount;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "bills")
    private List<BillProduct> billProducts;

    public Bills() {
    }

    public Bills(String customerName, String transactionId, double totalPrice, double gstAmount, double payableAmount) {
        this.customerName = customerName;
        this.transactionId = transactionId;
        this.totalPrice = totalPrice;
        this.gstAmount = gstAmount;
        this.payableAmount = payableAmount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getGstAmount() {
        return gstAmount;
    }

    public void setGstAmount(double gstAmount) {
        this.gstAmount = gstAmount;
    }

    public double getPayableAmount() {
        return payableAmount;
    }

    public void setPayableAmount(double payableAmount) {
        this.payableAmount = payableAmount;
    }

    public List<BillProduct> getBillProducts() {
        return billProducts;
    }

    public void setBillProducts(List<BillProduct> billProducts) {
        this.billProducts = billProducts;
    }
}
