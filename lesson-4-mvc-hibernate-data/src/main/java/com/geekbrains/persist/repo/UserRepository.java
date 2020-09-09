package com.geekbrains.persist.repo;

import com.geekbrains.persist.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findByLogin(String login);

    List<User> findByLoginLike(String loginPattern);
}
