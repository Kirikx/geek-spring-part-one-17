package com.geekbrains.controller;

import com.geekbrains.persist.entity.Product;
import com.geekbrains.persist.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public String allProducts(Model model, @RequestParam(name = "title", required = false) String title, @RequestParam(name = "min_cost", required = false) Integer min_cost, @RequestParam(name = "max_cost", required = false) Integer max_cost) {

        // todo фильтровать продукт по названию и по минимальной и максимальной его цене
        List<Product> allProducts;

        if (title != null && min_cost == null && max_cost == null) {
            allProducts = productRepository.findByTitleLike("%" + title + "%");
        } else if (title == null && min_cost != null && max_cost == null) {
            allProducts = productRepository.findByPriseGreaterThanEqual(new BigDecimal(min_cost));
        } else if (title == null && min_cost == null && max_cost != null) {
            allProducts = productRepository.findByPriseLessThanEqual(new BigDecimal(max_cost));
        } else if (title != null && min_cost != null && max_cost == null) {
            allProducts = productRepository.findByTitleLikeAndPriseGreaterThanEqual("%" + title + "%", new BigDecimal(min_cost));
        } else if (title == null && min_cost != null && max_cost != null) {
            allProducts = productRepository.findByPriseGreaterThanEqualAndPriseLessThanEqual(new BigDecimal(min_cost), new BigDecimal(max_cost));
        } else if (title != null && min_cost == null && max_cost != null) {
            allProducts = productRepository.findByTitleLikeAndPriseLessThanEqual("%" + title + "%", new BigDecimal(max_cost));
        } else if (title != null && min_cost != null && max_cost != null) {
            allProducts = productRepository.findByTitleLikeAndPriseGreaterThanEqualAndPriseLessThanEqual("%" + title + "%", new BigDecimal(min_cost), new BigDecimal(max_cost));
        } else {
            allProducts = productRepository.findAll();
        }

        model.addAttribute("products", allProducts);
        return "products";
    }

    @GetMapping("/create")
    public String createProduct(Model model) {
        Product product = new Product(-1, "", new BigDecimal("0.0"));
        model.addAttribute("product", product);
        return "product";
    }

    @GetMapping("/{id}")
    public String editProduct(@PathVariable("id") Integer id, Model model) {
        Product product = productRepository.findById(id).get();
        model.addAttribute("product", product);
        return "product";
    }

    @PostMapping("/update")
    public String updateProduct(Product product) {
        productRepository.save(product);
        return "redirect:/product";
    }

    @GetMapping("/{id}/delete")
    public String deleteProduct(@PathVariable("id") Integer id) {
        productRepository.deleteById(id);
        return "redirect:/product";
    }
}
