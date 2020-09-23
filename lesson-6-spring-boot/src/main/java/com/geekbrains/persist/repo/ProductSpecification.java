package com.geekbrains.persist.repo;

import com.geekbrains.persist.entity.Product;
import org.springframework.data.jpa.domain.Specification;

public final class ProductSpecification {

    public static Specification<Product> trueLiteral() {
        return (root, query, builder) -> builder.isTrue(builder.literal(true));
    }

    public static Specification<Product> titleLike(String title) {
        return (root, query, builder) -> builder.like(root.get("title"), "%" + title + "%");
    }

    public static Specification<Product> priseGreaterThanOrEqual(Integer minCost) {
        return (root, query, builder) -> builder.greaterThanOrEqualTo(root.get("prise"), minCost);
    }

    public static Specification<Product> priseLessThanOrEqual(Integer maxCost) {
        return (root, query, builder) -> builder.lessThanOrEqualTo(root.get("prise"), maxCost);
    }
}
