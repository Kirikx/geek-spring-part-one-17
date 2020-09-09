package com.geekbrains.persist.repo;

import com.geekbrains.persist.entity.Product;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ProductRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void insert(Product product) {
        em.persist(product);
    }

    @Transactional
    public void update(Product product) {
        em.merge(product);
    }

    @Transactional
    public void delete(Long id) {
        Product product = em.find(Product.class, id);
        if (product != null) {
            em.remove(product);
        }
    }

    public Product findByTitle(String title) {
        return em.createQuery("from Product where title = :title", Product.class)
                .setParameter("title", title)
                .getSingleResult();
    }

    public Product findById(Long id) throws SQLException {
        return em.find(Product.class, id);
    }

    public List<Product> getAllProducts() throws SQLException {
        return em.createQuery("from Product", Product.class).getResultList();
    }

}
