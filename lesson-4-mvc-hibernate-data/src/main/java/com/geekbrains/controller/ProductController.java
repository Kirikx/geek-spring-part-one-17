package com.geekbrains.controller;

import com.geekbrains.persist.entity.Product;
import com.geekbrains.persist.repo.ProductRepository;
import com.geekbrains.persist.repo.ProductSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

@Controller
@RequestMapping("/product")
public class ProductController {

    private static final int INITIAL_PAGE = 1;
    private static final int INITIAL_PAGE_SIZE = 5;
    private static final int[] PAGE_SIZES = {5, 10};
    private static final String INIT_SORT_FIELD = "id";

    private final static Logger logger = LoggerFactory.getLogger(UserController.class);


    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public String allProducts(Model model,
                              @RequestParam(name = "title", required = false) String title,
                              @RequestParam(name = "min_cost", required = false) Integer minCost,
                              @RequestParam(name = "max_cost", required = false) Integer maxCost,
                              @RequestParam("page") Optional<Integer> page,
                              @RequestParam("size") Optional<Integer> size,
                              @RequestParam("sortBy") Optional<String> sortBy,
                              @RequestParam("sortDirection") Optional<Sort.Direction> sortDirection) {

        logger.info("Filtering by title {} min_cost {} max_cost {}", title, minCost, maxCost);
        logger.info("Pagination: page {} size {} ", page, size);
        logger.info("Sorting: sortBy {} sortDirection {} ", sortBy, sortDirection);

        if (productRepository.count() != 0) {
            //
        } else {
            addToRepository();
        }

        Sort.Direction direction = sortDirection.orElse(Sort.Direction.ASC);

        PageRequest pageRequest = PageRequest.of(
                page.orElse(INITIAL_PAGE) - 1,
                size.orElse(INITIAL_PAGE_SIZE),
                direction,
                sortBy.orElse(INIT_SORT_FIELD));

        Specification<Product> spec = ProductSpecification.trueLiteral();

        if (title != null && !title.isEmpty()) {
            spec = spec.and(ProductSpecification.titleLike(title));
        }
        if (minCost != null) {
            spec = spec.and(ProductSpecification.priseGreaterThanOrEqual(minCost));
        }
        if (maxCost != null) {
            spec = spec.and(ProductSpecification.priseLessThanOrEqual(maxCost));
        }

        model.addAttribute("productsPage", productRepository.findAll(spec, pageRequest));
        model.addAttribute("sortDirection", direction);
        model.addAttribute("sortReversDirection", direction.isAscending() ? "DESC" : "ASC");
        model.addAttribute("sortBy", sortBy.orElse(INIT_SORT_FIELD));
        model.addAttribute("pageSize", PAGE_SIZES);
        return "products";
    }

    @GetMapping("/create")
    public String createProduct(Model model) {
        Product product = new Product();
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

    public void addToRepository() {
        for (int i = 0; i < 20; i++) {
            //below we are adding users to our repository for the sake of this example
            Product product = new Product();
            product.setTitle("Apple" + i);
            product.setPrise(new BigDecimal(10 + i));
            productRepository.save(product);
        }
    }
}
