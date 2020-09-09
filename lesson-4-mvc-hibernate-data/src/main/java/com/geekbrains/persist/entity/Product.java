package com.geekbrains.persist.entity;

import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "title", length = 128)
    private String title;

    @Column(name = "cost")
    private BigDecimal prise;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Order> orders;

    public Product() {
    }

    public Product(int id, String title, BigDecimal prise) {
        this.id = id;
        this.title = title;
        this.prise = prise;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrise() {
        return prise;
    }

    public void setPrise(BigDecimal prise) {
        this.prise = prise;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", cost=" + prise +
                ", orders=" + orders +
                '}';
    }
}
