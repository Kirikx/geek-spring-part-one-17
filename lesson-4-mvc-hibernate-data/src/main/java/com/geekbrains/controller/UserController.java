package com.geekbrains.controller;

import com.geekbrains.persist.entity.User;
import com.geekbrains.persist.repo.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String allUsers(Model model, @RequestParam(value = "name", required = false) String name) {
        logger.info("Filtering by name {}", name);
        List<User> allUsers;
        if (name == null || name.isEmpty()) {
            allUsers = userRepository.findAll();
        } else {
            allUsers = userRepository.findByLoginLike("%" + name + "%");
        }
        model.addAttribute("users", allUsers);
        return "users";
    }

    @GetMapping("/create")
    public String createUser(Model model) {
        User user = new User(-1, "", "", null);
        model.addAttribute("user", user);
        return "user";
    }

    @GetMapping("/{id}")
    public String editUser(@PathVariable("id") Integer id, Model model) {
        User user = userRepository.findById(id).get();
        model.addAttribute("user", user);
        return "user";
    }

    @PostMapping("/update")
    public String updateUser(User user) {
        userRepository.save(user);
        return "redirect:/user";
    }

    @GetMapping("/{id}/delete")
    public String deleteUser(@PathVariable("id") Integer id) {
        userRepository.deleteById(id);
        return "redirect:/user";
    }
}
