package com.geekbrains.controller;

import com.geekbrains.persist.entity.Order;
import com.geekbrains.persist.repo.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    private final static Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping
    public String allUsers(Model model) {
        List<Order> orderItems = orderRepository.findAll();
        model.addAttribute("order", orderItems);
        return "orderitems";
    }
}
