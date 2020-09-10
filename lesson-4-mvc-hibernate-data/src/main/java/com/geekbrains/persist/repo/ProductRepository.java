package com.geekbrains.persist.repo;

import com.geekbrains.persist.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByTitleLike(String titlePattern);

    List<Product> findByPriseGreaterThanEqual(BigDecimal minCost);

    List<Product> findByPriseGreaterThanEqualAndPriseLessThanEqual(BigDecimal minCost, BigDecimal maxCost);

    List<Product> findByPriseLessThanEqual(BigDecimal maxCost);

    List<Product> findByTitleLikeAndPriseGreaterThanEqualAndPriseLessThanEqual(String title, BigDecimal minCost, BigDecimal maxCost);

    List<Product> findByTitleLikeAndPriseGreaterThanEqual(String title, BigDecimal minCost);

    List<Product> findByTitleLikeAndPriseLessThanEqual(String title, BigDecimal maxCost);

}
