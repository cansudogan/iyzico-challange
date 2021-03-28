package com.iyzico.challenge.entity;

import javax.persistence.*;
import java.math.BigDecimal;


@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "productId")
    private long productId;

    @Column(name = "productName")
    private String productName;

    @Column(name = "productDescription")
    private String productDescription;

    @Column(name = "remainingStock")
    private int remainingStock;

    @Column(name = "productPrice")
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

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
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
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", productDescription='" + productDescription + '\'' +
                ", remainingStock=" + remainingStock +
                ", productPrice=" + productPrice +
                '}';
    }
}
