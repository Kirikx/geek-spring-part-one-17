package com.geekbrains.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import com.geekbrains.server.auth.AuthService;
import com.geekbrains.server.auth.AuthServiceJdbcImpl;
import com.geekbrains.server.persistance.UserRepository;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration //EXAMPLE3 (Предпочтительный способ)
@ComponentScan(basePackages = "ru.geekbrains.server") //EXAMPLE3 + annotation (не так часто используется)
public class SpringConfig {

//    @Bean //EXAMPLE3 + annotation
//    public ChatServer chatServer() {
//        return new ChatServer();
//    }

    @Bean
    public AuthService authService(UserRepository userRepository) {
        return new AuthServiceJdbcImpl(userRepository);
    }

    @Bean
    public UserRepository userRepository(DataSource dataSource) throws SQLException {
        return new UserRepository(dataSource);
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds.setUsername("root");
        ds.setPassword("12345678");
        ds.setUrl("jdbc:mysql://localhost:3306/chat_auth?&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
        return ds;
    }
}
