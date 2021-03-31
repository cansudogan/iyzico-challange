package com.iyzico.challenge.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Basket {
    private long id;
    private long userId;
    private Status status;
    private BigDecimal total = null;

    private User user;
    private Set<ProductsInBasket> products = new HashSet<>();

    public enum Status {
        ACTIVE, APPLIED, COMPLETED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "user_id", insertable = false, updatable = false, nullable = false)
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "status", length = 16, nullable = false)
    @Enumerated(EnumType.STRING)
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
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

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getTotal() {
        if (total != null) {
            return total;
        }

        return total = products.stream()
                .map(ProductsInBasket::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
