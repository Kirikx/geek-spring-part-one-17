package com.geekbrains.controller;

import com.geekbrains.persist.entity.Role;
import com.geekbrains.persist.repo.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/")
@Controller
public class WelcomeController {

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping
    public String welcomePage() {
        return "welcome";
    }

    @GetMapping("/login")
    public String login() {
        if (roleRepository.count() == 0) {
            addToRoles();
        }
        return "login";
    }

    private void addToRoles() {
        Role roleUser = new Role();
        roleUser.setRoleName("ROLE_USER");
        roleRepository.save(roleUser);
        Role roleAdmin = new Role();
        roleAdmin.setRoleName("ROLE_ADMIN");
        roleRepository.save(roleAdmin);
        Role roleManager = new Role();
        roleManager.setRoleName("ROLE_MANAGER");
        roleRepository.save(roleManager);
    }
}
