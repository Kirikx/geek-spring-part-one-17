package com.geekbrains.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.geekbrains.persistence.User;
import com.geekbrains.persistence.UserRepository;

import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String allUsers(Model model) throws SQLException {
        List<User> allUsers = userRepository.getAllUsers();
        model.addAttribute("users", allUsers);
        return "users";
    }

    @GetMapping("/create")
    public String createUser(Model model) throws SQLException {
        User user = new User(-1, "", "");
        model.addAttribute("user", user);
        return "user";
    }

    @GetMapping("/{id}")
    public String editUser(@PathVariable("id") Long id, Model model) throws SQLException {
        User user = userRepository.findById(id);
        model.addAttribute("user", user);
        return "user";
    }

    @PostMapping("/update")
    public String updateUser(User user) throws SQLException {
        if (user.getId()==-1){
            userRepository.insert(user);
        }
        userRepository.update(user);
        return "redirect:/user";
    }

    @GetMapping("/{id}/delete")
    public String deleteUser(@PathVariable("id") Long id) throws SQLException {
        userRepository.deleteById(id);
        return "redirect:/user";
    }
}
