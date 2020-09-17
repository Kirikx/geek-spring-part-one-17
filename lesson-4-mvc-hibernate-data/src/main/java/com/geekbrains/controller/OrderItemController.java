package com.geekbrains.controller;

import com.geekbrains.persist.entity.OrderItem;
import com.geekbrains.persist.repo.OrderItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/orderitem")
public class OrderItemController {

    private final static Logger logger = LoggerFactory.getLogger(OrderItemController.class);

    @Autowired
    private OrderItemRepository orderItemRepository;

    @GetMapping
    public String allUsers(Model model) {
        List<OrderItem> orderItems = orderItemRepository.findAll();
        model.addAttribute("orderItems", orderItems);
        return "orderitems";
    }
}
