package com.geekbrains.controller;

import com.geekbrains.persist.entity.Product;
import com.geekbrains.persist.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public String allProducts(Model model)  {
        List<Product> allProducts = productRepository.getAllProducts();
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
    public String editProduct(@PathVariable("id") Long id, Model model)   {
        Product product = productRepository.findById(id);
        model.addAttribute("product", product);
        return "product";
    }

    @PostMapping("/update")
    public String updateProduct(Product product)   {
        if (product.getId()==-1){
            productRepository.insert(product);
        }
            productRepository.update(product);
        return "redirect:/product";
    }

    @GetMapping("/{id}/delete")
    public String deleteProduct(@PathVariable("id") Long id)   {
        productRepository.delete(id);
        return "redirect:/product";
    }
}
