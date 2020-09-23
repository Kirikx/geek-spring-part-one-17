package com.geekbrains.persist.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "orderItems")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;

    @Column(name = "cost")
    private BigDecimal cost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Order order;

    @Column(name = "datetime_order")
    private Date datetimeOrder;

    @Column(name = "count")
    private Integer count;

    public OrderItem() {
    }

    public OrderItem(Integer id, Product product, BigDecimal cost, Order order, Date datetimeOrder, Integer count) {
        this.id = id;
        this.product = product;
        this.cost = cost;
        this.order = order;
        this.datetimeOrder = datetimeOrder;
        this.count = count;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Date getDatetimeOrder() {
        return datetimeOrder;
    }

    public void setDatetimeOrder(Date datetimeOrder) {
        this.datetimeOrder = datetimeOrder;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", productId=" + product.getId() +
                ", cost=" + cost +
                ", orderId=" + order.getId() +
                ", datetimeOrder=" + datetimeOrder +
                ", count=" + count +
                '}';
    }
}
