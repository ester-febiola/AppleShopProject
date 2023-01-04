package com.ester.appleshop.Models;

public class MyOrderModel {
    String productName;
    String productPrice;
    String productDate;
    String productTime;
    String totalQuantity;
    int totalPrice;

    public MyOrderModel() {
    }

    public MyOrderModel(String productName, String productPrice, String productDate, String productTime, String totalQuantity, int totalPrice) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDate = productDate;
        this.productTime = productTime;
        this.totalQuantity = totalQuantity;
        this.totalPrice = totalPrice;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductDate() {
        return productDate;
    }

    public void setProductDate(String productDate) {
        this.productDate = productDate;
    }

    public String getProductTime() {
        return productTime;
    }

    public void setProductTime(String productTime) {
        this.productTime = productTime;
    }

    public String getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(String totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}
