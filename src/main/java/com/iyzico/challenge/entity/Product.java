package com.iyzico.challenge.entity;

import javax.persistence.*;
import java.math.BigDecimal;


@Entity
@Table(name = "product")
public class Product {
    private long id;
    private String productName;
    private String productDescription;
    private int remainingStock;
    private BigDecimal productPrice;

    public Product(String productName) {
        this.productName = productName;
    }

    public Product() {

    }

    public Product(String productName,
                   String productDescription,
                   int remainingStock,
                   BigDecimal productPrice) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.remainingStock = remainingStock;
        this.productPrice = productPrice;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public int getRemainingStock() {
        return remainingStock;
    }

    public void setRemainingStock(int remainingStock) {
        this.remainingStock = remainingStock;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }


    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", productDescription='" + productDescription + '\'' +
                ", remainingStock=" + remainingStock +
                ", productPrice=" + productPrice +
                '}';
    }
}
