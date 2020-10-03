package com.geekbrains.persist.repo;

import com.geekbrains.persist.entity.User;
import org.springframework.data.jpa.domain.Specification;

public final class UserSpecification {

    public static Specification<User> trueLiteral() {
        return (root, query, builder) -> builder.isTrue(builder.literal(true));
    }

    public static Specification<User> loginLike(String login) {
        return (root, query, builder) -> builder.like(root.get("login"), "%" + login + "%");
    }

    public static Specification<User> emailLike(String email) {
        return (root, query, builder) -> builder.like(root.get("email"), "%" + email + "%");
    }
}
