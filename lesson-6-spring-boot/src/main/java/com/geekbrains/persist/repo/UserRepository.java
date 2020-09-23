package com.geekbrains.persist.repo;

import com.geekbrains.persist.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

    List<User> findByLogin(String login);

    List<User> findByLoginLike(String loginPattern);

    List<User> findByEmailLike(String email);

    List<User> findByLoginLikeAndEmailLike(String login, String email);

    @Query("from User u " +
            "where (u.email = :email or :email is null) and" +
            "      (u.login = :login or :login is null)")
    List<User> queryByLoginLikeAndEmailLike(@Param("login") String login, @Param("email") String email);
}
