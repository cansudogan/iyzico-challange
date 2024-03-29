package com.iyzico.challenge.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class ProductsInBasket implements Comparable<ProductsInBasket> {
    private long id;
    private long basketId;
    private long productId;
    private int count;
    private LocalDateTime createTime;

    private Basket basket;
    private Product product;

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
    @Column(name = "basket_id", insertable = false, updatable = false, nullable = false)
    public long getBasketId() {
        return basketId;
    }

    public void setBasketId(long basketId) {
        this.basketId = basketId;
    }

    @Basic
    @Column(name = "product_id", insertable = false, updatable = false, nullable = false)
    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    @Basic
    @Column(name = "count", nullable = false)
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Basic
    @Column(name = "create_time", nullable = false)
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    @ManyToOne(targetEntity = Basket.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "basket_id", referencedColumnName = "id")
    public Basket getBasket() {
        return basket;
    }

    public void setBasket(Basket basket) {
        this.basket = basket;
    }

    @ManyToOne(targetEntity = Product.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Transient
    public BigDecimal getSubtotal() {
        return getProduct().getProductPrice().multiply(new BigDecimal(count));
    }

    @Override
    public int compareTo(ProductsInBasket o) {
        return Long.compare(productId, o.getBasketId());
    }
}
