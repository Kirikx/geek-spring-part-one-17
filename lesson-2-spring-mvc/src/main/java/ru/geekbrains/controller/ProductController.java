package ru.geekbrains.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.geekbrains.persistance.Product;
import ru.geekbrains.persistance.ProductRepository;

import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public String allProducts(Model model) throws SQLException {
        List<Product> allProducts = productRepository.getAllProducts();
        model.addAttribute("products", allProducts);
        return "products";
    }

    @GetMapping("/create")
    public String createProduct(Model model) throws SQLException {
        Product product = new Product(-1, "", 0L);
        model.addAttribute("product", product);
        return "product";
    }

    @GetMapping("/{id}")
    public String editProduct(@PathVariable("id") Long id, Model model) throws SQLException {
        Product product = productRepository.findById(id);
        model.addAttribute("product", product);
        return "product";
    }

    @PostMapping("/update")
    public String updateProduct(Product product) throws SQLException {
        if (product.getId()==-1){
            productRepository.insert(product);
        }
            productRepository.update(product);
        return "redirect:/product";
    }

    @GetMapping("/{id}/delete")
    public String deleteProduct(@PathVariable("id") Long id) throws SQLException {
        productRepository.deleteById(id);
        return "redirect:/product";
    }
}
