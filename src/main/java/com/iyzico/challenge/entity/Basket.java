package com.iyzico.challenge.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Basket {
    private long id;
    private Status status;
    private BigDecimal totalPrice;
    private long userId;

    private User user;
    private Set<ProductsInBasket> products = new HashSet<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Column(name = "totalPrice")
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn (name = "user_id", referencedColumnName="id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @OneToMany(targetEntity = ProductsInBasket.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "basket", orphanRemoval = true)
    public Set<ProductsInBasket> getProducts() {
        return products;
    }

    public void setProducts(Set<ProductsInBasket> products) {
        this.products = products;
    }

    @Column(name = "user_id", insertable = false, updatable = false, nullable = false)
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Basket{" +
                "id=" + id +
                ", status=" + status +
                ", totalPrice=" + totalPrice +
                ", userId=" + userId +
                ", products=" + products +
                '}';
    }

    public enum Status {
        ACTIVE, APPLIED, COMPLETED
    }
}
