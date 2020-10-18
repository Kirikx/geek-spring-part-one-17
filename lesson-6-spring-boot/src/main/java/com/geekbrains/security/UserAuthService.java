package com.geekbrains.security;

import com.geekbrains.persist.entity.Role;
import com.geekbrains.persist.entity.User;
import com.geekbrains.persist.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


public class UserAuthService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserAuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return findByLogin(username)
//                .map(user -> new org.springframework.security.core.userdetails.User(
//                        user.getLogin(),
//                        user.getPassword(),
//                        Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")))) //TODO user.setRoles
//                .orElseThrow(()-> new UsernameNotFoundException(String.format("User %s not found", username)));
//    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findByLogin(username)
                .map(user -> new org.springframework.security.core.userdetails.User(
                        user.getLogin(),
                        user.getPassword(),
                        mapRolesToAuthorities(user.getRoles())))
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found", username)));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles) {
        return roles.stream().map(a -> new SimpleGrantedAuthority(a.getRoleName())).collect(Collectors.toList());
    }
}
